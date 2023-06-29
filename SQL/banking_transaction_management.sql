--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, 
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `balance` decimal(12,0) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `deposits`
--

DROP TABLE IF EXISTS `deposits`;

CREATE TABLE `deposits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `transaction_amount` decimal(12,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjs92lljr0s0ns05eip5oitkni` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `transfers`
--

DROP TABLE IF EXISTS `transfers`;

CREATE TABLE `transfers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `fees` int(11) NOT NULL,
  `fees_amount` decimal(12,0) NOT NULL,
  `transaction_amount` decimal(12,0) NOT NULL,
  `transfer_amount` decimal(12,0) NOT NULL,
  `recipient_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk3hx2mycbqj5wo0r21toog320` (`recipient_id`),
  KEY `FKi9ytbf7yb0unoomysw7rugr72` (`sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `withdraws`
--

DROP TABLE IF EXISTS `withdraws`;

CREATE TABLE `withdraws` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `transaction_amount` decimal(12,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8kyh1dt0vp46wslywn1dah2h9` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DELIMITER //
CREATE PROCEDURE `WithdrawMoney`(
    IN customer_id BIGINT,
    IN transaction_amount DECIMAL(10, 2)
)
BEGIN
    DECLARE current_balance DECIMAL(10, 2);
    SELECT balance INTO current_balance FROM customers WHERE id = customer_id;
    IF current_balance >= transaction_amount THEN
        UPDATE `customers`
        SET balance = balance - transaction_amount,
            updated_at = NOW()
        WHERE id = customer_id;
        INSERT INTO `withdraws` (created_at, customer_id, transaction_amount)
        VALUES (NOW(), customer_id, transaction_amount);
        SELECT * FROM customers WHERE id = customer_id;
    ELSE
        SELECT 'Insufficient balance' AS error_message;
    END IF;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE TransferMoney(
    IN sender_id BIGINT,
    IN recipient_id BIGINT,
    IN transaction_amount DECIMAL(10, 2)
)
BEGIN
    DECLARE sender_balance DECIMAL(10, 2);
    DECLARE transaction_fee DECIMAL(10, 2);
    SELECT balance INTO sender_balance FROM customers WHERE id = sender_id;
    SET transaction_fee = transaction_amount * 0.1;
    IF sender_balance >= transaction_amount + transaction_fee THEN
        UPDATE customers
        SET balance = balance - (transaction_amount + transaction_fee),
            updated_at = NOW()
        WHERE id = sender_id;
        UPDATE customers
        SET balance = balance + transaction_amount,
            updated_at = NOW()
        WHERE id = recipient_id;
        INSERT INTO transfers (created_at, sender_id, recipient_id, fees, fees_amount, transaction_amount, transfer_amount)
        VALUES (NOW(), sender_id, recipient_id, '0.1', transaction_fee, transaction_amount, transaction_amount + transaction_fee);
        SELECT * FROM customers WHERE id = sender_id;
    ELSE
        SELECT 'Insufficient balance' AS error_message;
    END IF;
END //
DELIMITER ;


CREATE VIEW transfer_history AS
SELECT t.id AS transfer_id,
       c.id AS sender_id,
       c.full_name AS sender_name,
       r.id AS recipient_id,
       r.full_name AS recipient_name,
       t.created_at,
       t.fees,
       t.fees_amount,
       t.transaction_amount,
       t.transfer_amount
FROM transfers t
JOIN customers c ON t.sender_id = c.id
JOIN customers r ON t.recipient_id = r.id;


CREATE VIEW deposits_history AS
SELECT c.id AS customer_id,
       c.full_name AS customer_name,
       d.transaction_amount AS transaction_amount,
       c.balance AS balance
FROM deposits d
JOIN customers c ON d.customer_id = c.id;

CREATE VIEW withdraws_history AS
SELECT c.id AS customer_id,
       c.full_name AS customer_name,
       w.transaction_amount AS transaction_amount,
       c.balance AS balance
FROM withdraws w
JOIN customers c ON w.customer_id = c.id;



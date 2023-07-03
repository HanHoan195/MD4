package com.cg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Date;
@Entity
@Table(name = "deposits")
public class Deposits {
    @Id
    private Long id;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "create_by")
    private Long createdBy;
    private boolean deleted;
    @Column(name = "update_at")
    private Date updateAt;
    @Column(name = "update_by")
    private Long updateBy;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;

    public Deposits() {
    }

    public Deposits(Long id, Date createAt, Long createdBy, boolean deleted, Date updateAt, Long updateBy, Long customerId, BigDecimal transactionAmount) {
        this.id = id;
        this.createAt = createAt;
        this.createdBy = createdBy;
        this.deleted = deleted;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }



}

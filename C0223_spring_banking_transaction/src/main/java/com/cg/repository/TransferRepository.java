package com.cg.repository;

import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository  extends JpaRepository<Transfer, Long> {
}

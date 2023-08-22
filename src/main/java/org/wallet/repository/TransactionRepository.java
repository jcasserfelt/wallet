package org.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wallet.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long accountId1, Long accountId2);
    // Other custom queries if needed
}

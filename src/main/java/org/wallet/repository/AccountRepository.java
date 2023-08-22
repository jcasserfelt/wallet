package org.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wallet.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);



    Long getBalanceByAccountNumber(String accountNumber);
}

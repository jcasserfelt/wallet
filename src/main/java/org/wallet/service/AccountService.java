package org.wallet.service;

import org.wallet.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> getAccountByAccountNumber(String accountNumber);

    Account createAccount(String accountNumber, Double balance);

    void updateAccount(Account account);

    List<Account> getAllAccounts();
}

package org.wallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wallet.exception.AccountAlreadyExistsException;
import org.wallet.exception.AccountNotFoundException;
import org.wallet.model.Account;
import org.wallet.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService{

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }

        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);

        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account with account number '" + accountNumber + "' not found");
        }

        return accountOptional;
    }

    @Transactional
    public Account createAccount(String accountNumber, Double balance) {
        // Validate input
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }

        if (balance == null || balance <= 0) {
            throw new IllegalArgumentException("Invalid initial balance");
        }

        // Check if an account with the provided account number already exists
        Optional<Account> existingAccount = accountRepository.findByAccountNumber(accountNumber);
        if (existingAccount.isPresent()) {
            throw new AccountAlreadyExistsException(accountNumber);
        }

        // Create and save the new account
        Account newAccount = new Account(accountNumber, balance);
        return accountRepository.save(newAccount);
    }

    @Transactional
    public void updateAccount(Account account) {
        // Retrieve the existing account from the database
        Account existingAccount = accountRepository.findById(account.getId())
                .orElseThrow(() -> new AccountNotFoundException(account.getAccountNumber()));

        // Update the account balance
        existingAccount.setBalance(account.getBalance());

        // Save the updated account back to the database
        accountRepository.save(existingAccount);
    }

    
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

}

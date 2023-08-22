package org.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wallet.exception.AccountNotFoundException;
import org.wallet.exception.InsufficientBalanceException;
import org.wallet.model.Account;
import org.wallet.model.Transaction;
import org.wallet.model.TransactionType;
import org.wallet.repository.AccountRepository;
import org.wallet.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountService accountService,
            AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            Long accountId = account.getId();

            // search for both source and target account
            return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);
        } else {
            throw new AccountNotFoundException(accountNumber);
        }
    }

    @Transactional
    @Override
    public Transaction makeDeposit(String accountNumber, double amount) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(accountNumber);
        }

        Account account = optionalAccount.get();

        // Update account balance
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        accountService.updateAccount(account);

        // Create and save transaction record
        Transaction depositTransaction = new Transaction(
                LocalDateTime.now(),
                TransactionType.DEPOSIT,
                amount,
                null, // no source account when depositing them chips
                account);

        return transactionRepository.save(depositTransaction);
    }

    @Transactional
    @Override
    public Transaction makeWithdrawal(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Optional<Account> optionalAccount = accountService.getAccountByAccountNumber(accountNumber);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(accountNumber);
        }

        Account account = optionalAccount.get();

        // Check if sufficient balance for withdrawal
        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException(accountNumber);
        }

        // Update account balance
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        accountService.updateAccount(account);

        // Create and save transaction record
        Transaction withdrawalTransaction = new Transaction(
                LocalDateTime.now(),
                TransactionType.WITHDRAWAL,
                amount,
                account,
                null); // no target account when withdrawing

        return transactionRepository.save(withdrawalTransaction);
    }

    @Transactional
    @Override
    public Transaction makeTransfer(String sourceAccountNumber, String targetAccountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Optional<Account> optionalSourceAccount = accountService.getAccountByAccountNumber(sourceAccountNumber);
        Optional<Account> optionalTargetAccount = accountService.getAccountByAccountNumber(targetAccountNumber);

        if (optionalSourceAccount.isEmpty()) {
            throw new AccountNotFoundException(sourceAccountNumber);
        }
        if (optionalTargetAccount.isEmpty()) {
            throw new AccountNotFoundException(targetAccountNumber);
        }

        Account sourceAccount = optionalSourceAccount.get();
        Account targetAccount = optionalTargetAccount.get();

        // Check if source account has sufficient balance for transfer
        if (sourceAccount.getBalance() < amount) {
            throw new InsufficientBalanceException(sourceAccountNumber);
        }

        // Update balances for source and target accounts
        double newSourceBalance = sourceAccount.getBalance() - amount;
        double newTargetBalance = targetAccount.getBalance() + amount;
        sourceAccount.setBalance(newSourceBalance);
        targetAccount.setBalance(newTargetBalance);

        // Create and save transaction record for the transfer
        Transaction transferTransaction = new Transaction(
                LocalDateTime.now(),
                TransactionType.TRANSFER,
                amount,
                sourceAccount,
                targetAccount);

        // Save updated accounts and the transfer transaction
        accountService.updateAccount(sourceAccount);
        accountService.updateAccount(targetAccount);
        transactionRepository.save(transferTransaction);

        return transferTransaction;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        try {
            return transactionRepository.findAll();
        } catch (DataAccessException ex) {
            // Handle database-related exception
            throw new RuntimeException("An error occurred while retrieving transactions", ex);
        }
    }

}

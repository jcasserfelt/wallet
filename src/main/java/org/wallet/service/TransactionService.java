package org.wallet.service;

import org.wallet.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsByAccountNumber(String accountNumber);

    Transaction makeDeposit(String accountNumber, double amount);

    Transaction makeWithdrawal(String accountNumber, double amount);

    Transaction makeTransfer(String sourceAccountNumber, String targetAccountNumber, double amount);

    List<Transaction> getAllTransactions();
}

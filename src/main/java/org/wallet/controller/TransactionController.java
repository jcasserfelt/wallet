package org.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wallet.model.Transaction;
import org.wallet.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/byAccountNumber/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/deposits")
    public ResponseEntity<Object> makeDeposit(@RequestParam String accountNumber, @RequestParam double amount) {
        Transaction depositTransaction = transactionService.makeDeposit(accountNumber, amount);
        return ResponseEntity.ok(depositTransaction);
    }


    @PostMapping("/withdrawals")
    public ResponseEntity<Object> makeWithdrawal(@RequestParam String accountNumber, @RequestParam double amount) {
        Transaction withdrawalTransaction = transactionService.makeWithdrawal(accountNumber, amount);
        return ResponseEntity.ok(withdrawalTransaction);
    }

    @PostMapping("/transfers")
    public ResponseEntity<Object> makeTransfer(
            @RequestParam String sourceAccountNumber,
            @RequestParam String targetAccountNumber,
            @RequestParam double amount) {

        Transaction transferTransaction = transactionService.makeTransfer(sourceAccountNumber, targetAccountNumber, amount);
        return ResponseEntity.ok(transferTransaction);
    }


}

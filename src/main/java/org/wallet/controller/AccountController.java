package org.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.wallet.exception.AccountNotFoundException;
import org.wallet.model.Account;
import org.wallet.service.AccountService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Double> getBalance(@PathVariable String accountNumber) {
        Optional<Account> account = accountService.getAccountByAccountNumber(accountNumber);

        if (account.isPresent()) {
            return ResponseEntity.ok(account.get().getBalance());
        } else {
            throw new AccountNotFoundException(accountNumber);
        }
    }


    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestParam String accountNumber,
            @RequestParam Double balance) {
        Account newAccount = accountService.createAccount(accountNumber, balance);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{accountNumber}")
                .buildAndExpand(newAccount.getAccountNumber())
                .toUri();
        return ResponseEntity.created(location).body(newAccount);
    }

}

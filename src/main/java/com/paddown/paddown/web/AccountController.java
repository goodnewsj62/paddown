package com.paddown.paddown.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paddown.paddown.data.Account;
import com.paddown.paddown.services.AccountService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account){
        Account createdAccount =  accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
    @GetMapping
    public ResponseEntity<HttpStatus> get(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

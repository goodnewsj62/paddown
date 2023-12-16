package com.paddown.paddown.services;

import java.util.Optional;

import com.paddown.paddown.data.Account;

public interface AccountService {
    Optional<Account>  getAccountByUsername(String username);
    Optional<Account> getAccountById(long id);
    Account createAccount(Account user);
    Optional<Account>  updateAccount(Account user);
}
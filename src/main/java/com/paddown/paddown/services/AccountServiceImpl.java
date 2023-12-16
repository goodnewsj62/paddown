package com.paddown.paddown.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paddown.paddown.data.Account;

@Service
public class  AccountServiceImpl implements AccountService{

    @Override
    public Optional<Account> getAccountById(long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<Account> getAccountByUsername(String username) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Account createAccount(Account user) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Optional<Account> updateAccount(Account user) {
        // TODO Auto-generated method stub
        return null;
    }
}
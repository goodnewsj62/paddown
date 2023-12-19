package com.paddown.paddown.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paddown.paddown.data.Account;
import com.paddown.paddown.error.CustomDataViolationException;
import com.paddown.paddown.respository.AccountRepo;

@Service
public class  AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Account createAccount(Account user)  throws CustomDataViolationException {
        // user.setActive(false);
        if(accountRepo.findByEmail(user.getEmail()).orElse(null) != null){
            throw new CustomDataViolationException("Account with email " +  user.getEmail() +  " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accountRepo.save(user);
    }
    @Override
    public Optional<Account> updateAccount(Account user) {
        // TODO Auto-generated method stub
        return null;
    }
}
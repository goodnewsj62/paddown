package com.paddown.paddown.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.paddown.paddown.data.Account;

public interface AccountService {
    Account  getAccountByUsername(String username);
    Account getAccountById(long id);
    Account createAccount(Account user);
    Account  updateAccount(Account user);
    void saveProfileImage(MultipartFile file, String username);
    Resource  getProfileImage(String username);
}
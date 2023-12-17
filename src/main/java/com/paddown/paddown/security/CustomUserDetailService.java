package com.paddown.paddown.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paddown.paddown.data.Account;
import com.paddown.paddown.respository.AccountRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    AccountRepo accountRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<Account> account =accountRepository.findByEmail(username);

        if(account.isPresent()){
            // return CustomUserDetail(account.get();)
        }
        // throw new UsernameNotFoundException("");
        return null;
    }
}

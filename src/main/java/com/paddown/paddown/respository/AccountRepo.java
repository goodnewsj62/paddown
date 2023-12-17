package com.paddown.paddown.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paddown.paddown.data.Account;

public interface AccountRepo extends JpaRepository<Account ,  Long> {
    Optional<Account> findByEmail(String email);
}
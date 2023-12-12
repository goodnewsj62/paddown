package com.paddown.paddown.respository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.paddown.paddown.data.Account;

public interface AccountRepo extends PagingAndSortingRepository<Account ,  Long> {

}
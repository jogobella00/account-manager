package com.account.manager.am.service;

import com.account.manager.am.dao.AccountRepository;
import com.account.manager.am.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountServiceImpl
 * Consists all implementations of the methods to operate on Account entities
 */
@Service
public class AccountServiceImpl implements AccountService {

    // JpaRepository
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }
}

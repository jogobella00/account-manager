package com.account.manager.am.service;

import com.account.manager.am.model.Account;
import com.account.manager.am.model.Transaction;

/**
 * AccountService
 * Consists all methods to operate on Account entities
 */
public interface AccountService {

    void addTransaction(Account account, Transaction transaction);
    void sumTransactions(Account account);
}

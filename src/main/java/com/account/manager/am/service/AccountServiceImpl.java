package com.account.manager.am.service;

import com.account.manager.am.dao.AccountRepository;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

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

    /**
     * The method is adding new transaction to the account
     * after it, method sumTransactions(Account account) is summing up all
     * values of transactions to get proper balance of the account
     * @param account
     * @param transaction
     */
    @Override
    public void addTransaction(Account account, Transaction transaction) {
            if (account.getTransactions() == null) { // if there are no Transactions yet
                account.setTransactions(new ArrayList<>()); // create empty List
            }
            account.getTransactions().add(transaction); // then add Transaction to the List
            sumTransactions(account); // sum up all Transactions
    }

    /**
     * The method is adding all transactions of the account and setting this sum as balance
     * @param account
     */
    @Override
    public void sumTransactions(Account account) {
        account.setBalance(account.getTransactions().stream()
                .map(Transaction::getValueOfTransaction) // get valueOfTransaction of each Transaction in the list
                .reduce(BigDecimal.ZERO, BigDecimal::add)); // add all of them, starting from;
    }
}

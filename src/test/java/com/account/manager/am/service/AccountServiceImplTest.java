package com.account.manager.am.service;

import com.account.manager.am.dao.AccountRepository;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceImplTest {


    AccountService accountService;
    AccountRepository accountRepository;

    Account account;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountRepository);

    }

    @Test
    public void addTransactionTest() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(BigDecimal.valueOf(1000)));
        account = Account.builder()
                .accountId(1)
                .balance(BigDecimal.valueOf(1000))
                .transactions(transactionList)
                .build();

       accountService.addTransaction(account, new Transaction(BigDecimal.valueOf(-500)));

       assertEquals(account.getBalance(), BigDecimal.valueOf(500));
       assertEquals(2, account.getTransactions().size());
    }

    @Test
    public void addTransaction_EmptyTransactionListTest() {
        account = Account.builder()
                .accountId(1)
                .balance(BigDecimal.valueOf(0))
                .build();

        accountService.addTransaction(account, new Transaction(BigDecimal.valueOf(-500)));

        assertEquals(account.getBalance(), BigDecimal.valueOf(-500));
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    public void sumTransactionTest() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(BigDecimal.valueOf(1000)));
        transactionList.add(new Transaction(BigDecimal.valueOf(1000)));
        account = Account.builder()
                .accountId(1)
                .balance(BigDecimal.valueOf(1000))
                .transactions(transactionList)
                .build();
        accountService.sumTransactions(account);

        assertEquals(BigDecimal.valueOf(2000), account.getBalance());

    }


}
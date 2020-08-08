package com.account.manager.am.service;

import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceImplTest {

    CustomerService customerService;
    CustomerRepository customerRepository;
    AccountService accountService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, accountService);
    }

    @Test
    public void addAccountTest() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(BigDecimal.valueOf(1000)));
        Account account = Account.builder()
                .accountId(1)
                .balance(BigDecimal.valueOf(1000))
                .transactions(transactionList)
                .build();
        Customer customer = new Customer("bob", "test");
        customerService.addAccount(customer, account);

        assertEquals(1, customer.getAccounts().size());;
    }
}

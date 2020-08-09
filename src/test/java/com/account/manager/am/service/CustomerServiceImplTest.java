package com.account.manager.am.service;

import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Customer Business Layer
 */
@DisplayName("CustomerService Tests")
public class CustomerServiceImplTest {

    CustomerService customerService;
    CustomerRepository customerRepository;
    AccountService accountService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, accountService);
    }

    // test adding new Account to the Customer by Service
    @Test
    @DisplayName("Adding Account")
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

package com.account.manager.am.service;

import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.exception.CustomerIdNotFoundException;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * CustomerServiceImpl
 * Consists all implementations of the methods to operate on Customer entities
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    /**
     * get Customer from DB by customerId
     * @param customerId
     * @return
     */
    @Override
    public Customer getCustomerById(int customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException(customerId)); //if not found throw exception
    }

    /**
     * The method is receiving customerId and initialCredit
     * If Customer is not found by ID, CustomerIdNotFoundException is thrown
     * After Customer is found, there is created new Account and related to it Transaction
     * then the Account is saved to current Customer and Customer is saved (updated) in the DB
     * @param customerId int value
     * @param initialCredit BigDecimal
     */
    @Override
    public void saveNewAccount(int customerId, BigDecimal initialCredit) {

        Customer customer = getCustomerById(customerId); // find Customer in the DB
        Account newAccount = new Account(); // create new Account and related Transaction
        accountService.addTransaction(newAccount, new Transaction(initialCredit)); // add Transaction to the Account
        addAccount(customer, newAccount); // add new Account to Customer
        customerRepository.save(customer); // save Customer with all child entities
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * The method is adding new Account to the Customer
     * @param customer
     * @param account
     */
    @Override
    public void addAccount(Customer customer, Account account) {
        if (customer.getAccounts() == null) { // if there is are no Accounts for the Customer
            customer.setAccounts(new ArrayList<>()); // create empty list
        }
        customer.getAccounts().add(account); // add Account passed in the argument
    }
}

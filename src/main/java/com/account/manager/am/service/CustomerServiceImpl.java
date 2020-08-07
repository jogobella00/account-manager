package com.account.manager.am.service;

import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.exception.CustomerIdNotFoundException;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    @Override
    public Customer getCustomerById(int customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException(customerId));
    }

    @Override
    public void saveNewAccount(int customerId, BigDecimal initialCredit) {

        Customer customer = new Customer();

        try {
            customer = getCustomerById(customerId);
        } catch (CustomerIdNotFoundException e) {
            throw new CustomerIdNotFoundException(customerId);
        }

        Account newAccount = new Account(initialCredit);
        Transaction newTransaction = new Transaction(initialCredit);

        customer.addAccount(newAccount);
        newAccount.addTransaction(newTransaction);

        accountService.save(newAccount);
        customerRepository.saveAndFlush(customer);
    }
}

package com.account.manager.am.service;

import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.exception.CustomerIdNotFoundException;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerIdNotFoundException(customerId));
    }

    @Override
    public void saveNewAccount(int customerId, double initialCredit) {
        // TODO: make it better
        // TODO: add transactions
        Customer cust = customerRepository
                .findById(customerId).orElseThrow();
        cust.addAccount(new Account(initialCredit));
        customerRepository.save(cust);
    }
}

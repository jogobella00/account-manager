package com.account.manager.am.service;

import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;

import java.math.BigDecimal;
import java.util.List;

/**
 * CustomerService
 * Consists all methods to operate on Customer entities
 */
public interface CustomerService {

    Customer getCustomerById(int customerId);
    void saveNewAccount(int customerId, BigDecimal initialCredit);
    Customer save(Customer customer);
    void addAccount(Customer customer, Account account);
    List<Customer> findAll();
}

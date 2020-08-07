package com.account.manager.am.service;

import com.account.manager.am.model.Customer;

import java.math.BigDecimal;

public interface CustomerService {

    Customer getCustomerById(int customerId);
    void saveNewAccount(int customerId, BigDecimal initialCredit);
}

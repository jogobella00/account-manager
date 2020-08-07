package com.account.manager.am.exception;

/**
 * Custom exception used when customerId in customers table is not found
 */
public class CustomerIdNotFoundException extends RuntimeException {

    public CustomerIdNotFoundException(int id) {
        super("Customer with: customerId=" + id + " not found.");
    }
}

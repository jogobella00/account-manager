package com.account.manager.am.exception;

public class CustomerIdNotFoundException extends RuntimeException {

    public CustomerIdNotFoundException(int id) {
        super("Customer with: customerId=" + id + " not found.");
    }
}

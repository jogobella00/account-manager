package com.account.manager.am.exception;

/**
 * Custom exception used when initialCredit equals 0 at /customer/{customerId}/account/ endpoint
 */
public class WrongInitialCreditException extends RuntimeException {

    public WrongInitialCreditException(String message) {
        super(message);
    }
}

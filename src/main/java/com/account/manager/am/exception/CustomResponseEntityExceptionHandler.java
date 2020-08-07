package com.account.manager.am.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class for managing throwing custom exceptions
 */
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST) // response code 400
    @ExceptionHandler(WrongInitialCreditException.class) // when WrongInitialCreditException is thrown
    ResponseEntity<Object> handleWrongInitialCredit(WrongInitialCreditException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND) // response code 404
    @ExceptionHandler(CustomerIdNotFoundException.class) // when CustomerIdNotFoundException is thrown
    ResponseEntity<Object> handleCustomerIdNotFound(CustomerIdNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }
}

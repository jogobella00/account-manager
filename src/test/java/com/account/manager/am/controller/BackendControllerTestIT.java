package com.account.manager.am.controller;

import com.account.manager.am.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getCustomerTest() {
        Customer customer = restTemplate.getForObject("/v1/customer/1", Customer.class);

        assertEquals(customer.getCustomerId(), 1);
    }

    @Test
    void createNewAccountTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/customer/1/account?initialCredit=1111", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void createNewAccount_initialCreditZeroTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/customer/1/account?initialCredit=0", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertTrue(response.getBody().contains("You cannot do transfer with no money!"));
    }

    @Test
    void createNewAccount_initialCreditTooLongTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/customer/1/account?initialCredit=9999999999", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertTrue(response.getBody().contains("You want to transfer too much money! Maximum amount is 999999999"));
    }
}

package com.account.manager.am.controller;

import com.account.manager.am.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing web layer
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Web layer controller Test")
public class CustomerControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Check if call for the ID is returning the same
     */
    @Test
    @DisplayName("Test output ID")
    void getCustomerTest() {
        Customer customer = restTemplate.getForObject("/v1/customer/1", Customer.class);

        assertEquals(1 ,customer.getCustomerId());
    }

    /**
     * Test status code after proper call of endpoint
     */
    @Test
    @DisplayName("Response after proper call createNewAccount()")
    void createNewAccountTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/customer/1/account?initialCredit=1111", String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Test status code after sending initialCredit=0
     */
    @Test
    @DisplayName("0 as initialCredit")
    void createNewAccount_initialCreditZeroTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/customer/1/account?initialCredit=0", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("You cannot do transfer with no money!"));
    }

    /**
     * Test status code after sending initialCredit=9999999999 (too big number)
     */
    @DisplayName("Too big number as initial Credit")
    @Test
    void createNewAccount_initialCreditTooLongTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/customer/1/account?initialCredit=9999999999", String.class);
        assertEquals(HttpStatus.BAD_REQUEST ,response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("You want to transfer too much money! Maximum amount is 999999999"));
    }
}

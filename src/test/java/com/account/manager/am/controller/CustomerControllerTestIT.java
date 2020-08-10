package com.account.manager.am.controller;

import com.account.manager.am.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing web layer
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Web layer CustomerController Test")
public class CustomerControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    String queryParam1 = "initialCredit";
    String queryParam2 = "accountName";

    /**
     * Check if call for the ID is returning the same
     */
    @Test
    @DisplayName("Test output entity")
    void getCustomerTest() {
        Customer customer = restTemplate.getForObject("/v1/customer/1", Customer.class);

        assertEquals(1, customer.getCustomerId());
        assertEquals("test", customer.getFirstName());
        assertEquals("test2", customer.getLastName());
        assertEquals(2054.32, customer.getBalance());
        assertEquals(2, customer.getAccounts().size());
        assertEquals(2, customer.getAccounts().get(0).getTransactions().size());
    }

    /**
     * Test status code after proper call of endpoint
     */
    @Test
    @DisplayName("Response after proper call createNewAccount()")
    void createNewAccountTest() {
        ResponseEntity<String> response = restTemplate
                .postForEntity("/v1/customer/1/account?" + queryParam1 + "=1111&" + queryParam2 + "=test",
                        String.class,
                        String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Test status code after sending initialCredit=0
     */
    @Test
    @DisplayName("0 as initialCredit")
    void createNewAccount_initialCreditZeroTest() {
        ResponseEntity<String> response = restTemplate
                .postForEntity("/v1/customer/1/account?" + queryParam1 + "=0&" + queryParam2 + "=test",
                        String.class,
                        String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("You cannot do transfer with no money!"));
    }

    /**
     * Test status code after sending initialCredit=9999999999 (too big number)
     */
    @Test
    @DisplayName("Too big number as initial Credit")
    void createNewAccount_initialCreditTooLongTest() {
        ResponseEntity<String> response = restTemplate
                .postForEntity("/v1/customer/1/account?initialCredit=9999999999&" + queryParam2 + "=test",
                        String.class,
                        String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("You want to transfer too much or too less money! The amount should be between 999999999 and -999999999"));
    }

    /**
     * Test status code after sending initialCredit=abc (not a number)
     */
    @Test
    @DisplayName("initial Credit not a number")
    void createNewAccount_initialCreditNotANumberTest() {
        ResponseEntity<String> response = restTemplate
                .postForEntity("/v1/customer/1/account?initialCredit=abc&" + queryParam2 + "=test",
                        String.class,
                        String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("initialCredit has to be a number."));
    }
}

package com.account.manager.am.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Web layer CustomerFrontendController Test")
public class CustomerFrontendControllerTestIT {

    @Autowired
    TestRestTemplate restTemplate;

    String queryParam1 = "initialCredit";
    String queryParam2 = "accountName";

    @Test
    @DisplayName("saveTransfer, customer not found exception")
    void saveTransfer_CustomerIdNotFoundTest() {
        int id = 4;
        ResponseEntity<String> response = restTemplate
                .postForEntity("/saveTransfer/" + id + "/account?" + queryParam1 + "=1000&" + queryParam2 + "=test", String.class, String.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertTrue(response.getBody().contains("Customer with: customerId=" + id + " not found."));
    }

    @Test
    @DisplayName("saveTransfer, initialCredit zero exception")
    void saveTransfer_InitialCreditZeroTest() {
        int id = 1;
        ResponseEntity<String> response = restTemplate
                .postForEntity("/saveTransfer/" + id + "/account?" + queryParam1 + "=0&" + queryParam2 + "=test", String.class, String.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("You cannot do transfer with no money!"));
    }

    @Test
    @DisplayName("saveTransfer, initialCredit not a number exception")
    void saveTransfer_InitialCreditNotANumberTest() {
        int id = 1;
        ResponseEntity<String> response = restTemplate
                .postForEntity("/saveTransfer/" + id + "/account?" + queryParam1 + "=abc&" + queryParam2 + "=test", String.class, String.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("initialCredit has to be a number."));
    }
}

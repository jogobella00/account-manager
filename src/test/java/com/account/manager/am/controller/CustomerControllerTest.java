package com.account.manager.am.controller;

import com.account.manager.am.exception.WrongInitialCreditException;
import com.account.manager.am.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoInteractions;


/**
 * Tests of controller methods and how do they use service methods
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Test CustomerController use of service methods")
public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    /**
     * Check if on proper call, Service is used once and response is proper code
     * when customerController.createNewAccount(customerId, valueOfTransfer, accountName) called
     */
    @Test
    @DisplayName("once used createNewAccount()")
    public void createNewAccount_serviceUsedOnce() {
        ResponseEntity<Object> response = customerController.createNewAccount(1, BigDecimal.valueOf(1000), "test");
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        then(customerService).should(times(1)).saveNewAccount(1, BigDecimal.valueOf(1000), "test");
    }

    /**
     * If exception is thrown, service is not used
     */
    @Test
    @DisplayName("createNewAccount(), no interactions when exception thrown")
    public void createNewAccount_serviceNoUse_ZeroInitialCredit() {
        assertThrows(WrongInitialCreditException.class, () -> customerController.createNewAccount(1, BigDecimal.valueOf(0), "test"));
        verifyNoInteractions(customerService);
    }

    /**
     * Service should be used once when customerController.getCustomer(customerId) called
     */
    @Test
    @DisplayName("getCustomer(), used once")
    public void getCustomerById_serviceUsedOnce() {
        customerController.getCustomer(1);
        then(customerService).should(times(1)).getCustomerById(1);
    }
}

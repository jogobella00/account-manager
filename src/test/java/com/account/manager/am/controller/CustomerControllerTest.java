package com.account.manager.am.controller;

import com.account.manager.am.exception.CustomResponseEntityExceptionHandler;
import com.account.manager.am.exception.CustomerIdNotFoundException;
import com.account.manager.am.exception.WrongInitialCreditException;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import com.account.manager.am.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Tests for methods in BackendController
 * Checking if they're returning proper Object and throwing proper Exceptions
 */
@WebMvcTest(CustomerController.class)
@DisplayName("Test BackendController method")
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Mock
    CustomerController customerController;

    @Autowired
    MockMvc mockMvc;

    Customer validCustomer;

    @BeforeEach
    void setUp() {
        // create Transactions and Account for validCustomer
        Transaction transaction = new Transaction(BigDecimal.valueOf(9999));
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Account account = new Account(BigDecimal.valueOf(9999));
        account.setTransactions(transactions);
        // create Customer object
        validCustomer = Customer.builder()
                .customerId(1)
                .firstName("bob")
                .lastName("smith")
                .balance(9999)
                .accounts(new ArrayList<>(List.of(account)))
                .build();
        // set up BackendController with CustomResponseEntityExceptionHandler
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomResponseEntityExceptionHandler(), customerController).build();
    }

    /**
     * Check if getCustomerById() is returning proper JSON
     * @throws Exception
     */
    @Test
    @DisplayName("GET Customer by ID - check output JSON")
    void getCustomerByIdTest() throws Exception {
        given(customerController.getCustomer(anyInt())).willReturn(validCustomer);

        mockMvc.perform(get("/v1/customer/" + validCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId", is(validCustomer.getCustomerId())))
                .andExpect(jsonPath("$.firstName", is(validCustomer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(validCustomer.getLastName())))
                .andExpect(jsonPath("$.balance", is(validCustomer.getBalance())))
                .andExpect(jsonPath("$.accounts[0].balance",
                        is(validCustomer.getAccounts()
                                .get(0)
                                .getBalance()
                                .intValue())))
                .andExpect(jsonPath("$.accounts[0].transactions[0].valueOfTransaction",
                        is(validCustomer.getAccounts()
                                .get(0)
                                .getTransactions()
                                .get(0)
                                .getValueOfTransaction()
                                .intValue())));
    }

    /**
     * Test if CustomerIdNotFoundException is thrown and if it's with proper message
     * @throws Exception
     */
    @Test
    @DisplayName("GET Customer by ID - not found")
    public void getCustomerById_IdNotFoundTest() throws Exception {
        int customerId = 4;
        when(customerController.getCustomer(customerId)).thenThrow(new CustomerIdNotFoundException(customerId));

        mockMvc.perform(get("/v1/customer/" + customerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Customer with: customerId=" + customerId + " not found.")));
    }

    /**
     * Test if ConstraintViolationException is thrown and if it's with proper message
     * @throws Exception
     */
    @Test
    @DisplayName("GET create new Account - too big number")
    public void createNewAccount_initialCreditTooLongTest() throws Exception {
        when(customerController.createNewAccount(1, BigDecimal.valueOf(1000000000))).thenThrow(new ConstraintViolationException("",null));

        mockMvc.perform(get("/v1/customer/1/account")
                .queryParam("initialCredit","1000000000"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("You want to transfer too much money! Maximum amount is 999999999")));
    }

    /**
     * Test if WrongInitialCreditException is thrown and if it's with proper message
     */
    @Test
    @DisplayName("GET create new Account - initialCredit 0")
    public void createNewAccount_initialCreditIsZeroTest() throws Exception {
        when(customerController.createNewAccount(1, BigDecimal.valueOf(0))).thenThrow(new WrongInitialCreditException("You cannot do transfer with no money!"));

        mockMvc.perform(get("/v1/customer/1/account")
                .queryParam("initialCredit","0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("You cannot do transfer with no money!")));
    }

}

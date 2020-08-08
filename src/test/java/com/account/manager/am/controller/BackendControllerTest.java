package com.account.manager.am.controller;

import com.account.manager.am.exception.CustomResponseEntityExceptionHandler;
import com.account.manager.am.exception.CustomerIdNotFoundException;
import com.account.manager.am.exception.WrongInitialCreditException;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import com.account.manager.am.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
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


//TODO: add comments to all the tests
@WebMvcTest(BackendController.class)
public class BackendControllerTest {

    @MockBean
    CustomerService customerService;

    @Mock
    BackendController backendController;

    @Autowired
    MockMvc mockMvc;

    Customer validCustomer;

    @BeforeEach
    void setUp() {
        validCustomer = Customer.builder()
                .customerId(1)
                .firstName("bob")
                .lastName("smith")
                .balance(123456.12)
                .accounts(new ArrayList<>(List.of(new Account(new Transaction(BigDecimal.valueOf(9999))))))
                .build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomResponseEntityExceptionHandler(), backendController).build();
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        given(backendController.getCustomer(anyInt())).willReturn(validCustomer);

        mockMvc.perform(get("/v1/customer/" + validCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId", is(validCustomer.getCustomerId())))
                .andExpect(jsonPath("$.firstName", is(validCustomer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(validCustomer.getLastName())))
                .andExpect(jsonPath("$.balance", is(validCustomer.getBalance())));
    }

    @Test
    public void getCustomerById_IdNotFoundTest() throws Exception {
        when(backendController.getCustomer(4)).thenThrow(new CustomerIdNotFoundException(4));

        mockMvc.perform(get("/v1/customer/4"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Customer with: customerId=4 not found.")));
    }

    @Test
    public void createNewAccount_initialCreditTooLongTest() throws Exception {
        when(backendController.createNewAccount(1, BigDecimal.valueOf(1000000000))).thenThrow(new ConstraintViolationException("",null));

        mockMvc.perform(get("/v1/customer/1/account")
                .queryParam("initialCredit","1000000000"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("You want to transfer too much money! Maximum amount is 999999999")));
    }

    @Test
    public void createNewAccount_initialCreditIsZeroTest() throws Exception {
        when(backendController.createNewAccount(1, BigDecimal.valueOf(0))).thenThrow(new WrongInitialCreditException("You cannot do transfer with no money!"));

        mockMvc.perform(get("/v1/customer/1/account")
                .queryParam("initialCredit","0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("You cannot do transfer with no money!")));
    }

}

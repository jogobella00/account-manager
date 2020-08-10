package com.account.manager.am.controller;

import com.account.manager.am.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

/**
 * Tests of controller methods and how do they use service methods
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Test CustomerFrontendController use of business methods")
public class CustomerFrontendControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerFrontendController customerFrontendController;

    @Mock
    Model model;

    /**
     * Check if returned String redirects to proper html file
     */
    @Test
    @DisplayName("home redirect")
    void homePageTest() {
        String homePage = customerFrontendController.homePage();
        assertEquals("home", homePage);
    }

    /**
     * Check order of used objects for customerFrontendController.listCustomers(model)
     * And if were called proper methods with arguments
     */
    @Test
    @DisplayName("listCustomers()")
    void listCustomersTest() {
        InOrder inOrder = inOrder(customerService, model); // 1st customerService, 2nd model
        String customerListView = customerFrontendController.listCustomers(model); // use controller method
        assertThat("list-customers").isEqualTo(customerListView); // check if method returned what desired
        inOrder.verify(customerService).findAll(); // check if customerService was called with proper method
        inOrder.verify(model).addAttribute(anyString(), anyList()); // check if model was call with proper method and arguments
    }

    /**
     * Check order of used objects for customerFrontendController.showCustomerDetails(customerId, model)
     * And if were called proper methods with arguments
     */
    @Test
    @DisplayName("showCustomerDetails()")
    void showCustomerDetailsTest() {
        InOrder inOrder = inOrder(customerService, model);
        String customerDetailsView = customerFrontendController.showCustomerDetails(1, model);
        assertThat("customer-details").isEqualTo(customerDetailsView);
        inOrder.verify(customerService).getCustomerById(1);
        inOrder.verify(model).addAttribute(anyString(), any());
    }

    /**
     * Check order of used objects for customerFrontendController.newTransfer(customerId, model)
     * And if were called proper methods with arguments
     */
    @Test
    @DisplayName("newTransfer()")
    void newTransferTest() {
        InOrder inOrder = inOrder(customerService, model);
        String newTransferView = customerFrontendController.newTransfer(1, model);
        assertThat("newTransfer").isEqualTo(newTransferView);
        inOrder.verify(customerService).getCustomerById(1);
        inOrder.verify(model).addAttribute(anyString(), any());
    }

    /**
     * Check order of used objects for customerFrontendController.saveTransfer(customerId, valueOfTransaction, accountName)
     * And if were called proper methods with arguments
     * and if service was called only once
     */
    @Test
    @DisplayName("if in saveTransfer(), service was called once ")
    void saveTransfer_calledOnce() {
        String customerDetailsView = customerFrontendController.saveTransfer(1, BigDecimal.valueOf(1000), "test");
        assertThat("redirect:/customer/list").isEqualTo(customerDetailsView);
        then(customerService).should(times(1)).saveNewAccount(1, BigDecimal.valueOf(1000), "test");
    }
}

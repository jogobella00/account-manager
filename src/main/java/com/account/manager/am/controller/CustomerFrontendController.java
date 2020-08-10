package com.account.manager.am.controller;

import com.account.manager.am.exception.WrongInitialCreditException;
import com.account.manager.am.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import java.math.BigDecimal;

/**
 * Controller for handling frontend of the application
 */
@Controller
public class CustomerFrontendController {

    private final CustomerService customerService;

    @Autowired
    public CustomerFrontendController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * host:8080 gets a 'home' page
     * @return resources/templates/home.html
     */
    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    /**
     * Show all the Customers in the DB
     * @param model
     * @return
     */
    @GetMapping("/customer/list")
    public String listCustomers(Model model) {

        model.addAttribute("customers", customerService.findAll());
        return "list-customers";
    }

    /**
     * Show Customer details by customerId
     * @param customerId
     * @param model
     * @return
     */
    @GetMapping("/showCustomerDetails")
    public String showCustomerDetails(@RequestParam("customerId") int customerId, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(customerId));
        return "customer-details";
    }

    /**
     * Go to page where you can add new Transfer
     * @param customerId
     * @param model
     * @return
     */
    @GetMapping("newTransfer")
    public String newTransfer(@RequestParam("customerId") int customerId, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(customerId));
        return "newTransfer";
    }

    /**
     * Endpoint to save a new Transfer
     * @param customerId
     * @param initialCredit
     * @param accountName
     * @return
     */
    @PostMapping("/saveTransfer/{customerId}/account")
    public String saveTransfer(@PathVariable("customerId") int customerId,
                               @RequestParam(value = "initialCredit") @Max(1000000000) @NumberFormat BigDecimal initialCredit,
                               @RequestParam(value = "accountName") String accountName) {
        if (initialCredit.equals(BigDecimal.valueOf(0))) {
            throw new WrongInitialCreditException("You cannot do transfer with no money!");
        } else {
            customerService.saveNewAccount(customerId,initialCredit, accountName);
        }
        return "redirect:/customer/list";
    }
}

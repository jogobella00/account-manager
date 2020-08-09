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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Controller
public class CustomerFrontendController {

    private final CustomerService customerService;

    @Autowired
    public CustomerFrontendController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/customer/list")
    public String listCustomers(Model model) {

        model.addAttribute("customers", customerService.findAll());
        return "list-customers";
    }

    @GetMapping("/showCustomerDetails")
    public String showCustomerDetails(@RequestParam("customerId") int customerId, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(customerId));
        return "customer-details";
    }

    @GetMapping("newTransfer")
    public String newTransfer(@RequestParam("customerId") int customerId, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(customerId));
        return "newTransfer";
    }

    @PostMapping("/saveTransfer/{customerId}")
    public String saveTransfer(@PathVariable("customerId") int customerId,
                               @RequestParam(value = "initialCredit") @Max(1000000000) @NumberFormat BigDecimal initialCredit) {
        if (initialCredit.equals(BigDecimal.valueOf(0))) {
            throw new WrongInitialCreditException("You cannot do transfer with no money!");
        } else {
            customerService.saveNewAccount(customerId,initialCredit);
        }
        return "redirect:/customer/list";
    }
}

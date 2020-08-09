package com.account.manager.am.controller;

import com.account.manager.am.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}

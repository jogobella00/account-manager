package com.account.manager.am.controller;

import com.account.manager.am.model.Customer;
import com.account.manager.am.service.AccountService;
import com.account.manager.am.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class BackendController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @Autowired
    public BackendController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @GetMapping("/customer/{customerId}")
    public Customer getCustomer(@PathVariable int customerId) {
        return customerService.getCustomerById(customerId);
    }

    // TODO: validation initialCredit > 0
    // TODO: improve endpoint naming
    @GetMapping("/account/{customerId}")
    public ResponseEntity<Object> createNewAccount(@PathVariable int customerId, @RequestParam int initialCredit) {
        customerService.saveNewAccount(customerId, initialCredit);
        return ResponseEntity.status(204).build();
    }
}

package com.account.manager.am.controller;

import com.account.manager.am.exception.WrongInitialCreditException;
import com.account.manager.am.model.Customer;
import com.account.manager.am.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import java.math.BigDecimal;

/**
 * Controller with endpoint to
 * - retrieve data about Customer and
 * - to add new account with initialCredit.
 * The response is in JSON.
 */
@Validated
@RestController
@RequestMapping("/v1")
public class CustomerController {

    // access to services through interfaces
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * By given customerId returns found customer with all related data
     * If not
     * @throws com.account.manager.am.exception.CustomerIdNotFoundException
     * @param customerId integer value
     * @return customer information: customerId, first name, last name, balance, transactions of the accounts
     *
     * {
     *     customerId: int
     *     firstName: String
     *     lastName: String
     *     balance: double
     *     accounts: List [
     *          {
     *              accountId: int
     *              balance: double
     *              transactions: List [
     *                  {
     *                   transactionId: int
     *                   valueOfTransaction: double
     *                  }
     *               ]
     *          }
     *     ]
     * }
     */
    @GetMapping("/customer/{customerId}")
    @ResponseBody
    public Customer getCustomer(@PathVariable int customerId) {
        return customerService.getCustomerById(customerId);
    }

    /**
     * Endpoint allows to create new Account of existing Customer
     * @throws com.account.manager.am.exception.CustomerIdNotFoundException if passed customerId cannot be found in the DB
     * @throws ConstraintViolationException handled in {@link com.account.manager.am.exception.CustomResponseEntityExceptionHandler}
     * if value of initialCredit is smaller then 1 (400 response)
     * @param customerId integer value
     * @param initialCredit BigDecimal value, bigger than 0
     * @return 204 empty response or 400 when ConstraintViolationException thrown
     */
    @GetMapping("/customer/{customerId}/account")
    public ResponseEntity<Object> createNewAccount(@PathVariable int customerId,
                                                   @RequestParam @Max(1000000000) BigDecimal initialCredit) {
        if (initialCredit.equals(BigDecimal.valueOf(0))) {
            throw new WrongInitialCreditException("You cannot do transfer with no money!");
        } else {
         customerService.saveNewAccount(customerId, initialCredit);
        }
       return ResponseEntity.status(204).build();
    }

}

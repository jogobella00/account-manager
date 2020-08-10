package com.account.manager.am.service;

import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.exception.CustomerIdNotFoundException;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Service methods that are using Repository methods
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Transactional
@DisplayName("Business + JpaRepository Tests")
public class JpaRepositoryTest {


    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    CustomerService customerService;

    Customer customer;
    Customer customerToTest;

    @BeforeEach
    void setUp() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(1000));
        Account account = new Account("testAccount");
        accountService.addTransaction(account, transaction);
        customer = new Customer("bobo", "srobo");
        customerService.addAccount(customer, account);
    }

    // test saving and findById by JpaRepository
    @Test
    @DisplayName("Save and findById by JpaRepo")
    public void saveAndFindByIdByRepositoryTest() {
        customer = customerRepository.save(customer);
        Customer foundOne = customerRepository.findById(customer.getCustomerId()).orElse(null);
        assertNotNull(foundOne);
        assertEquals(customer.getCustomerId(), foundOne.getCustomerId());
    }

    // test saving by CustomerService
    @Test
    @DisplayName("Save by Service")
    public void saveAndFindByIdByServiceTest() {
        customer = customerService.save(customer);
        Customer foundOne = customerService.getCustomerById(customer.getCustomerId());
        assertNotNull(foundOne);
        assertEquals(customer.getCustomerId(), foundOne.getCustomerId());
    }

    // test throwing proper exception if customerId not found
    @Test
    @DisplayName("Exception if Id not found")
    public void findByIdNotFoundTest() {
        customer = customerRepository.save(customer);
        assertThrows(CustomerIdNotFoundException.class, () -> customerService.getCustomerById(customer.getCustomerId()+1));
    }

    // test createNewAccount() method
    @Test
    @DisplayName("creating new Account")
    public void createNewAccountTest() {

        customerToTest = customerService.getCustomerById(1);
        customerService.saveNewAccount(1, BigDecimal.valueOf(1234), "test");

        Account newAccount = new Account("testAccount");
        Transaction newTransaction = new Transaction(BigDecimal.valueOf(1234));
        accountService.addTransaction(newAccount, newTransaction);
        customerService.addAccount(customerToTest, newAccount);

        Customer foundCustomer = customerService.getCustomerById(1);

        assertEquals(customerToTest.getBalance(), foundCustomer.getBalance());
        assertEquals(customerToTest.getAccounts().size(), foundCustomer.getAccounts().size());
    }

    // check if @Formula annotation and its SQL query in Customer class is working as desired
    @Test
    @DisplayName("Check annotation @Formula and query")
    public void customerBalanceFormulaCheck() {
        Customer customerToCheck = customerService.getCustomerById(1);
        assertNotNull(customerToCheck);
        BigDecimal balance = customerToCheck.getAccounts().stream().map(Account::getBalance) // get valueOfTransaction of each Transaction in the list
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(balance.doubleValue(), customerToCheck.getBalance());
    }

    // check if findAll() works properly
    @Test
    @DisplayName("FindAll()")
    void customerFindAllTest() {
        List<Customer> customerList = customerService.findAll();
        customerService.save(customer);
        List<Customer> biggerCustomerList = customerService.findAll();
        assertEquals(customerList.size()+1, biggerCustomerList.size());
    }
}

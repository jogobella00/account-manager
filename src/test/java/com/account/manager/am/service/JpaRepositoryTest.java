package com.account.manager.am.service;

import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.exception.CustomerIdNotFoundException;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
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
        Account account = new Account();
        accountService.addTransaction(account, transaction);
        customer = new Customer("bobo", "srobo");
        customerService.addAccount(customer, account);
    }


    @Test
    public void saveAndFindByIdByRepositoryTest() {

        customer = customerRepository.save(customer);
        Customer foundOne = customerRepository.findById(customer.getCustomerId()).orElse(null);
        assertNotNull(foundOne);
        assertEquals(customer.getCustomerId(), foundOne.getCustomerId());
    }

    @Test
    public void saveAndFindByIdByServiceTest() {

        customer = customerService.save(customer);
        Customer foundOne = customerService.getCustomerById(customer.getCustomerId());
        assertNotNull(foundOne);
        assertEquals(customer.getCustomerId(), foundOne.getCustomerId());
    }

    @Test
    public void findByIdNotFoundTest() {
        customer = customerRepository.save(customer);
        assertThrows(CustomerIdNotFoundException.class, () -> customerService.getCustomerById(customer.getCustomerId()+1));
    }

    @Test
    public void createNewAccountTest() {

        customerToTest = customerService.getCustomerById(1);
        customerService.saveNewAccount(1, BigDecimal.valueOf(1234));

        Account newAccount = new Account();
        Transaction newTransaction = new Transaction(BigDecimal.valueOf(1234));
        accountService.addTransaction(newAccount, newTransaction);
        customerService.addAccount(customerToTest, newAccount);

        Customer foundCustomer = customerService.getCustomerById(1);

        assertThat(customer.getBalance()==foundCustomer.getBalance());
        assertThat(customer.getAccounts().size()==foundCustomer.getAccounts().size());
    }

    @Test
    public void customerBalanceFormulaCheck() {
        Customer customerToCheck = customerService.getCustomerById(1);
        assertNotNull(customer);
        assertEquals(3, customerToCheck.getAccounts().size());
        assertEquals(3288.32, customerToCheck.getBalance());
    }
}

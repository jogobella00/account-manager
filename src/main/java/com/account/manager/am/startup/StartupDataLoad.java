package com.account.manager.am.startup;

import com.account.manager.am.dao.AccountRepository;
import com.account.manager.am.dao.CustomerRepository;
import com.account.manager.am.dao.TransactionRepository;
import com.account.manager.am.model.Account;
import com.account.manager.am.model.Customer;
import com.account.manager.am.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Class for specifying data to load into in-memory H2 database at startup of the application
 */
@Component
public class StartupDataLoad implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public StartupDataLoad(AccountRepository accountRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadNewCustomer("tom", "jag", BigDecimal. valueOf(1000), BigDecimal.valueOf(-500), BigDecimal.valueOf(1554.32));
        loadNewCustomer("bob", "smith", BigDecimal.valueOf(2000), BigDecimal.valueOf(10000), BigDecimal.valueOf(-1554.32));
        loadNewCustomer("mark", "buffalo", BigDecimal.valueOf(-321531), BigDecimal.valueOf(999999), BigDecimal.valueOf(-15912.12));
    }

    /**
     * The method is creating new Customer with Accounts and Transactions
     * @param firstName first name of the Customer - String
     * @param lastName last name of the Customer - String
     * @param valueOfTransaction1 - value of the first Transaction, will be assigned to Account no.1
     * @param valueOfTransaction2 - value of the second Transaction, will be assigned to Account no.1
     * @param valueOfTransaction3 - value of the third Transaction, will be assigned to Account no.2
     */
    public void loadNewCustomer(String firstName, String lastName, BigDecimal valueOfTransaction1, BigDecimal valueOfTransaction2, BigDecimal valueOfTransaction3) {

        Customer cust = new Customer(firstName, lastName); // create new Customer

        Transaction transaction2 = new Transaction(valueOfTransaction2); // created separated, the constructor of Account can receive only one argument with Transaction

        Account acc1 = new Account(new Transaction(valueOfTransaction1)); // create new Account no.1

        acc1.addTransaction(transaction2); // add to the Account no.1 Transaction

        Account acc2 = new Account(new Transaction(valueOfTransaction3)); // create new Account no.2
        cust.addAccount(acc1); // add Account no. 1 to the Customer
        cust.addAccount(acc2); // add Account no. 2 to the Customer

        customerRepository.save(cust); // save Customer into the DB
    }
}

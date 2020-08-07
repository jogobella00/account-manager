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

    public void loadNewCustomer(String firstName, String lastName, BigDecimal valueOfTransaction1, BigDecimal valueOfTransaction2, BigDecimal valueOfTransaction3) {

        Customer cust = new Customer(firstName, lastName);
        Transaction transaction1 = new Transaction(valueOfTransaction1);
        Transaction transaction2 = new Transaction(valueOfTransaction2);
        Transaction transaction3 = new Transaction(valueOfTransaction3);
        // TODO: do contructor with getting values of transactions
        Account acc1 = new Account(transaction1.getValueOfTransaction().add(transaction2.getValueOfTransaction()));

        Account acc2 = new Account(transaction3.getValueOfTransaction());
        cust.addAccount(acc1);
        cust.addAccount(acc2);

        acc1.addTransaction(transaction1);
        acc1.addTransaction(transaction2);
        acc2.addTransaction(transaction3);

        customerRepository.save(cust);
        accountRepository.save(acc1);
        accountRepository.save(acc2);
    }
}

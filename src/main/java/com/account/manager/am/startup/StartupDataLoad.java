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

@Component
public class StartupDataLoad implements ApplicationRunner {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public StartupDataLoad(AccountRepository accountRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Customer cust1 = new Customer("tom", "jag");
        Transaction transaction1 = new Transaction(1000);
        Transaction transaction2 = new Transaction(-500);
        Transaction transaction3 = new Transaction(1554.32);
        Account acc1 = new Account(transaction1.getValueOfTransaction() + transaction2.getValueOfTransaction());

        Account acc2 = new Account(transaction3.getValueOfTransaction());
        cust1.addAccount(acc1);
        cust1.addAccount(acc2);

        acc1.addTransaction(transaction1);
        acc1.addTransaction(transaction2);
        acc2.addTransaction(transaction3);

        customerRepository.save(cust1);
        accountRepository.save(acc1);
        accountRepository.save(acc2);
//        transactionRepository.save(transaction1);

    }
}

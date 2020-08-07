package com.account.manager.am.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Account entity is representing Account in Account-Manager app
 * It has fields of
 * - account_id populated by H2 DB,
 * - balance that is BigDecimal value specified by hibernate annotation as decimal(10,2),
 * represents amount of money on the Account,
 * - customer_id field is present in Customer class with help of annotation @OneToMany,
 * so one Customer can have many Accounts
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    @NotNull
    private int accountId;

    @Column(name = "balance", precision = 11, scale = 2)
    @NotNull
    private BigDecimal balance;

    // field present in transactions table (Transaction class)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions;

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    // constructor when creating new Account, is creating transaction related to it
    public Account(Transaction transaction) {
        this.balance = transaction.getValueOfTransaction();
        addTransaction(transaction);
    }

    // helper method to add in easier way new Transactions to Account entity
    // after each addition of Transaction, the new Transaction is calculated into total balance of the Account
    public void addTransaction(Transaction transaction) {

        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        setBalance(transactions.stream()
                .map(Transaction::getValueOfTransaction) // get valueOfTransaction of each Transaction in the list
                .reduce(BigDecimal.ZERO, BigDecimal::add)); // add all of them, starting from 0
    }
}

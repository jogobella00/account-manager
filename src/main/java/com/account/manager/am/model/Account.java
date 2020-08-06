package com.account.manager.am.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int accountId;
    @Column(name = "balance")
    private double balance;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions;

    public Account(double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {

        if (transactions == null) {
            transactions = new ArrayList<>();
        }

        transactions.add(transaction);
    }
}

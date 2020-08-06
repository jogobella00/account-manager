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
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;
    @Column(name = "valueOfTransaction")
    private double valueOfTransaction;
//
//    public void addAccount(Account account) {
//
//        if (accounts == null) {
//            accounts = new ArrayList<>();
//        }
//
//        accounts.add(account);
//    }

    public Transaction(double valueOfTransaction) {
        this.valueOfTransaction = valueOfTransaction;
    }
}

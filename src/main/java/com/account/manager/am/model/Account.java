package com.account.manager.am.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    @NotNull
    private int accountId;

    @Column(name = "balance", precision = 10, scale = 2)
    @NotNull
    private BigDecimal balance;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions;

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {

        if (transactions == null) {
            transactions = new ArrayList<>();
        }

        transactions.add(transaction);
    }
}

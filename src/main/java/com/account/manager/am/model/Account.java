package com.account.manager.am.model;

import lombok.*;

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
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    @NotNull
    private int accountId;

    // default length is 255
    @Column(name = "account_name")
    private String name;

    @Column(name = "balance", precision = 11, scale = 2)
    @NotNull
    private BigDecimal balance;

    // field present in transactions table (Transaction class)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions;

    public Account(String name, BigDecimal balance) {
        this.balance = balance;
        this.name = name;
    }

    public Account(String name) {
        this.name = name;
    }
}

package com.account.manager.am.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer entity is representing Customer in Account-Manager app
 * It has fields of
 * - customer_id populated by H2 DB,
 * - first_name mandatory String field,
 * - last_name mandatory String field
 * - balance double field, visible when retrieving Customer from the DB (getCustomerById method)
 * @Formula annotation specifying SQL query to get sum of balance in all related to Customer Accounts
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @NotNull
    private int customerId;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Formula("select sum(a.balance) from accounts a " +
            "where a.customer_id = customer_id")
    private double balance;

    // field is present in accounts table, annotations here specifying relation Customer <-> Account
    // name = 'customer_id' -> from accounts table
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Account> accounts;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // helper method to add in easier way new Accounts to Customer entity
    public void addAccount(Account account) {

        if (accounts == null) {
            accounts = new ArrayList<>();
        }

        accounts.add(account);
    }
}

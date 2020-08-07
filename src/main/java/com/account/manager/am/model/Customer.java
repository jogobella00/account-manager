package com.account.manager.am.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
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

    // name = 'customer_id' -> from accounts table
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Account> accounts;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addAccount(Account account) {

        if (accounts == null) {
            accounts = new ArrayList<>();
        }

        accounts.add(account);
    }
}

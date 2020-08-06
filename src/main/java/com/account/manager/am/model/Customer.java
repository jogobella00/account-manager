package com.account.manager.am.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;

    // name = 'customer_id' -> from accounts table
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Transaction> transactions;
//
    @Formula("select sum(a.balance) from accounts a where a.customer_id = customer_id")
    private float balance;
//    private List<Transaction> transactions;
}

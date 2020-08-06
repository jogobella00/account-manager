package com.account.manager.am.model;

import lombok.*;

import java.math.BigInteger;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    private BigInteger customerId;
    private String name;
    private String surname;
    private float balance;
    private List<Transaction> transactions;
}

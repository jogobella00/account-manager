package com.account.manager.am.model;

import lombok.*;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {

    private BigInteger accountId;
    private BigInteger customerId;
    private float balance;
}

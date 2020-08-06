package com.account.manager.am.model;

import lombok.*;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {

    private BigInteger transactionId;
    private BigInteger accountId;
    private float valueOfTransaction;
}

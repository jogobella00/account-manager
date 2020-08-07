package com.account.manager.am.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    @NotNull
    private int transactionId;

    @Column(name = "valueOfTransaction", precision = 10, scale = 2)
    @NotNull
    private BigDecimal valueOfTransaction;

    public Transaction(BigDecimal valueOfTransaction) {
        this.valueOfTransaction = valueOfTransaction;
    }
}

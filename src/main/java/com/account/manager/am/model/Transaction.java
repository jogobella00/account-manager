package com.account.manager.am.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Transaction entity is representing Transaction in Account-Manager app
 * It has fields of
 * - transaction_id populated by H2 DB,
 * - valueOfTransaction that is BigDecimal value specified by hibernate annotation as decimal(10,2),
 * represents amount of money transferred in this Transaction,
 * - account_id field is present in Account class with help of annotation @OneToMany,
 * so one Account can have many Transactions
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    @NotNull
    private int transactionId;

    @Column(name = "valueOfTransaction", precision = 11, scale = 2)
    @NotNull
    private BigDecimal valueOfTransaction;

    public Transaction(BigDecimal valueOfTransaction) {
        this.valueOfTransaction = valueOfTransaction;
    }
}

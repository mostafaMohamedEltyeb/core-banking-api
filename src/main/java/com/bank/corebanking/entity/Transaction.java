package com.bank.corebanking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String fromAccountNumber;

    @Column(nullable = false)
    private String toAccountNumber;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency = "SAR";

    @Column(nullable = false)
    private String status;

    private String remarks;

    @Column(nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();
}

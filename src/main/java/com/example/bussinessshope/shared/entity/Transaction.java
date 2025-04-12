package com.example.bussinessshope.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Transaction {
    LocalDateTime transactionTime;
    @Column(name = "COST_FACTOR")
    Double costFactor;
}

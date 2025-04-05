package com.example.bussinessshope.order.entity;

import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.shared.entity.Situation;
import com.example.bussinessshope.shared.entity.Transaction;
import com.example.bussinessshope.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false, nullable = false)
    long id;

    @Column(name = "CHECK_FACTOR_TIME", nullable = false)
    LocalDateTime checkFactorTime;

    @Embedded
    Transaction transaction;

    @ManyToMany
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    List<ProductEntity> productList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID_FK", nullable = false)
    UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "SITUATION", nullable = false, length = 10)
    Situation situation;
}


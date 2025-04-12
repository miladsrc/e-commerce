package com.example.bussinessshope.product.entity;

import com.example.bussinessshope.business.entity.BusinessEntity;
import com.example.bussinessshope.order.entity.OrderEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false, nullable = false)
    long id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    String name;

    @Column(name = "QUANTITY", nullable = false)
    int quantity;

    @Column(name = "PRICE", nullable = false)
    Double price;

    @ManyToMany(mappedBy = "productList")
    List<OrderEntity> orders;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "BUSINESS_ID_FK")
    BusinessEntity business;
}


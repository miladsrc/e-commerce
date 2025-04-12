package com.example.bussinessshope.business.entity;

import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.shared.entity.Address;
import com.example.bussinessshope.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Getter
@Setter
@SoftDelete(columnName = "DELETED")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "BUSINESS")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false, nullable = false)
    long id;

    @Column(name = "COMPANY_NAME", nullable = false)
    String name;

    @Column(name = "EMAIL", nullable = true, unique = true)
    String email;

    @Column(name = "PHONE_NUMBER", nullable = true, unique = true)
    String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "USER_ID_FK", nullable = false)
    UserEntity user;

    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
    List<ProductEntity> productList;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "business-address", joinColumns = @JoinColumn(name = "business-id"))
    List<Address> addressList;
}

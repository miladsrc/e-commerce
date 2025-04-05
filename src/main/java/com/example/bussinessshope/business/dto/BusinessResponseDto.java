package com.example.bussinessshope.business.dto;

import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.shared.entity.Address;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public
class BusinessResponseDto {
    long id;
    String name;
    String email;
    String phoneNumber;
    List<ProductEntity> productList;
    List<Address> addressList;
}

package com.example.bussinessshope.business.dto;

import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.shared.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUpdateDto {

    @NotBlank(message = "name can't be null")
    private String name;

    @Email(message = "not valid")
    private String email;

    @Size(min = 10, max = 15, message = "must be between 10 to 15")
    private String phoneNumber;

    private List<Address> addressList;
    private List<ProductEntity> productList;
}

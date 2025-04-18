package com.example.bussinessshope.business.dto;

import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.shared.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessUpdateDto {
    @Size(min = 3, max = 50, message = "Name must be between 3 to 50 characters")
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits")
    private String phoneNumber;
    private List<Address> addressList;
    private List<ProductEntity> productList;
}

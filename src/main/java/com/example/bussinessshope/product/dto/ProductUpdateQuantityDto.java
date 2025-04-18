package com.example.bussinessshope.product.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateQuantityDto {
    @Min(value = 0, message = "quantity cannot be equal to zero")
    private Integer quantity;
}

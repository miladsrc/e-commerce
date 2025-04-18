package com.example.bussinessshope.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {
    @Min(value = 0, message = "quantity cannot be less than 0")
    private Integer quantity;
    @DecimalMin(value = "0.0", inclusive = false, message = "price cannot be zero")
    private Double price;
}
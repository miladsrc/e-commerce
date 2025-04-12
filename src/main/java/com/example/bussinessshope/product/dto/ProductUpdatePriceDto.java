package com.example.bussinessshope.product.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdatePriceDto {

    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;
}

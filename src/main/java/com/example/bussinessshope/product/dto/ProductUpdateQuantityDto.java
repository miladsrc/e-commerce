package com.example.bussinessshope.product.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductUpdateQuantityDto {

    @Min(0)
    private Integer quantity;
}

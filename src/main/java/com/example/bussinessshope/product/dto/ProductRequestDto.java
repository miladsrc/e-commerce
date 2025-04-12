package com.example.bussinessshope.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    @NotNull(message = "Product name must not be null.")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters.")
    private String name;

    @Min(value = 0, message = "Quantity must be zero or positive.")
    private Integer quantity;

    @Min(value = 0, message = "Price must be zero or positive.")
    private Double price;
    @NotNull
    private Long businessId;
}

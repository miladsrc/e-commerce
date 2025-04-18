package com.example.bussinessshope.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "name cannot be blank")
    private String name;
    @Min(value = 0, message = "quantity cannot be less than 0")
    private Integer quantity;
    @DecimalMin(value = "0.0", inclusive = false, message = "minimum price of product cannot be equal to zero")
    private Double price;
    @NotNull(message = "business id cannot be null")
    private Long businessId;
}

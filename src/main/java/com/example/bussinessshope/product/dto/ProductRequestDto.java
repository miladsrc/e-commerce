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
    @NotBlank
    private String name;
    @Min(0)
    private Integer quantity;
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;
    @NotNull
    private Long businessId;
}

package com.example.bussinessshope.order.dto;

import com.example.bussinessshope.product.entity.ProductEntity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDto {
    @FutureOrPresent(message = "Check factor time cannot be in the past.")
    private LocalDateTime checkFactorTime;
    @Min(value = 0, message = "Cost factor cannot be negative.")
    private Double costFactor;
    @NotNull(message = "Product list must not be null.")
    @Size(min = 1, message = "At least one product must be selected.")
    private List<ProductEntity> productList;
}

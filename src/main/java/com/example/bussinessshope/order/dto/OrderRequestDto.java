package com.example.bussinessshope.order.dto;

import com.example.bussinessshope.shared.entity.Situation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "User ID must not be null.")
    private Long userId;
    @NotNull(message = "Product list must not be null.")
    @Size(min = 1, message = "At least one product must be selected.")
    private List<Long> productIds;
    @FutureOrPresent(message = "Check factor time cannot be in the past.")
    private LocalDateTime checkFactorTime;
    @Min(value = 0, message = "Cost factor cannot be negative.")
    private Double costFactor;
    @NotNull(message = "Order situation must not be null.")
    private Situation situation;
}

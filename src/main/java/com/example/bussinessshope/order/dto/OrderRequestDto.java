package com.example.bussinessshope.order.dto;

import com.example.bussinessshope.shared.entity.Situation;
import jakarta.validation.constraints.Min;
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
    private Long userId;
    private List<Long> productIds;
    private LocalDateTime checkFactorTime;
    @Min(0)
    private Double costFactor;
    private Situation situation;
}

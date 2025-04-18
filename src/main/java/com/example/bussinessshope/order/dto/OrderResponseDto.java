package com.example.bussinessshope.order.dto;

import com.example.bussinessshope.product.dto.ProductResponseDto;
import com.example.bussinessshope.shared.entity.Situation;
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
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private List<ProductResponseDto> productList;
    private LocalDateTime checkFactorTime;
    private Double costFactor;
    private Situation situation;
}



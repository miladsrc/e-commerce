package com.example.bussinessshope.order.dto;

import com.example.bussinessshope.product.entity.ProductEntity;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDto {
    LocalDateTime checkFactorTime;
    @Min(0)
    Double costFactor;
    List<ProductEntity> productList;
}

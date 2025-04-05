package com.example.bussinessshope.product.repository;

import com.example.bussinessshope.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByIdAndBusinessUserId(Long productId, Long userId);
}

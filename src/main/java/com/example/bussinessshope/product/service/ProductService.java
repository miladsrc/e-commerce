package com.example.bussinessshope.product.service;

import com.example.bussinessshope.business.entity.BusinessEntity;
import com.example.bussinessshope.business.repository.BusinessRepository;
import com.example.bussinessshope.product.dto.*;
import com.example.bussinessshope.product.entity.ProductEntity;
import com.example.bussinessshope.product.repository.ProductRepository;
import com.example.bussinessshope.shared.exception.specified.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BusinessRepository businessRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, BusinessRepository businessRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.businessRepository = businessRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ProductResponseDto addProduct(Long userId, ProductRequestDto productRequestDto) {
        BusinessEntity business = businessRepository.findByIdAndUserId(productRequestDto.getBusinessId(), userId)
                .orElseThrow(() -> new UnauthorizedException("User does not own this business"));
        if (business.getProductList() == null) {
            business.setProductList(new ArrayList<>());
        }
        ProductEntity product = ProductEntity.builder()
                .business(business)
                .name(productRequestDto.getName())
                .quantity(productRequestDto.getQuantity())
                .price(productRequestDto.getPrice())
                .orders(new ArrayList<>())
                .build();
        productRepository.saveAndFlush(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long userId, Long productId, ProductUpdateDto productUpdateDto) {
        ProductEntity product = productRepository.findByIdAndBusinessUserId(productId, userId)
                .orElseThrow(() -> new UnauthorizedException("User does not own this product"));

        if (productUpdateDto.getQuantity() != null) {
            product.setQuantity(productUpdateDto.getQuantity());
        }
        if (productUpdateDto.getPrice() != null) {
            product.setPrice(productUpdateDto.getPrice());
        }

        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Transactional
    public void softDeleteProduct(Long userId, Long productId) {
        ProductEntity product = productRepository.findByIdAndBusinessUserId(productId, userId)
                .orElseThrow(() -> new UnauthorizedException("User does not own this product"));

        productRepository.delete(product);
    }

    @Transactional
    public ProductResponseDto updatePrice(Long userId, Long productId, ProductUpdatePriceDto dto) {
        ProductEntity product = productRepository.findByIdAndBusinessUserId(productId, userId)
                .orElseThrow(() -> new UnauthorizedException("User does not own this product"));

        product.setPrice(dto.getPrice());
        productRepository.save(product);

        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Transactional
    public ProductResponseDto updateQuantity(Long userId, Long productId, ProductUpdateQuantityDto dto) {
        ProductEntity product = productRepository.findByIdAndBusinessUserId(productId, userId)
                .orElseThrow(() -> new UnauthorizedException("User does not own this product"));

        product.setQuantity(dto.getQuantity());
        productRepository.save(product);

        return modelMapper.map(product, ProductResponseDto.class);
    }

}

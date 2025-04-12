package com.example.bussinessshope.business.controller;

import com.example.bussinessshope.business.dto.BusinessCreateDto;
import com.example.bussinessshope.business.dto.BusinessResponseDto;
import com.example.bussinessshope.business.dto.BusinessUpdateDto;
import com.example.bussinessshope.business.service.BusinessService;
import com.example.bussinessshope.security.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;
    private final JwtService jwtService;

    @Autowired
    public BusinessController(BusinessService businessService, JwtService jwtService){
        this.businessService = businessService;
        this.jwtService = jwtService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BusinessResponseDto>> getAllByUserId(
            @RequestHeader("Authorization") String key) {
        Long userId = jwtService.extractUserIdFromToken(key);
        List<BusinessResponseDto> userResponseDTOS = businessService.getBusinessByUserId(userId);
        return ResponseEntity.ok(userResponseDTOS);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<List<BusinessResponseDto>> getAll() {
        List<BusinessResponseDto> userResponseDTOS = businessService.getAllBusiness();
        return ResponseEntity.ok(userResponseDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<BusinessResponseDto> create(
            @RequestHeader("Authorization") String key,
            @RequestBody @Valid BusinessCreateDto businessCreateDto) {
        String username = jwtService.extractUsername(jwtService.decryptToken(jwtService.extractToken(key)));
        BusinessResponseDto responseDto = businessService.createBusiness(businessCreateDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponseDto> getById(
            @RequestHeader("Authorization") String key,
            @PathVariable Long id) {
        Long userId = jwtService.extractUserIdFromToken(key);
        BusinessResponseDto businessResponseDto = businessService.getBusinessById(userId, id);
        return ResponseEntity.ok(businessResponseDto);
    }

    @PutMapping("/{businessId}")
    public ResponseEntity<BusinessResponseDto> update(
            @RequestHeader("Authorization") String key,
            @PathVariable Long businessId,
            @RequestBody @Valid BusinessUpdateDto businessUpdateDto) {
        Long userId = jwtService.extractUserIdFromToken(key);
        BusinessResponseDto responseDto = businessService.updateBusiness(businessUpdateDto, businessId, userId);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{businessId}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String key,
            @PathVariable Long businessId) {
        Long userId = jwtService.extractUserIdFromToken(key);
        businessService.deleteBusiness(userId, businessId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


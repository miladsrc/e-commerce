package com.example.bussinessshope.security.controller;

import com.example.bussinessshope.security.dto.AuthenticationRequest;
import com.example.bussinessshope.security.dto.AuthenticationResponse;
import com.example.bussinessshope.security.dto.RegisterCustomerRequest;
import com.example.bussinessshope.security.dto.RegisterSellerRequest;
import com.example.bussinessshope.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Register a new seller",
            description = "Registers a new seller and returns JWT token upon successful registration"
    )
    @ApiResponse(responseCode = "200", description = "Seller registered successfully")
    @PostMapping("/register/seller")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody @Valid RegisterSellerRequest request) {
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @Operation(
            summary = "Register a new customer",
            description = "Registers a new customer and returns JWT token upon successful registration"
    )
    @ApiResponse(responseCode = "200", description = "Customer registered successfully")
    @PostMapping("/register/customer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @RequestBody @Valid RegisterCustomerRequest request) {
        return ResponseEntity.ok(authenticationService.registerCustomer(request));
    }

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token if credentials are valid"
    )
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @PostMapping("/authenticate/user")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}

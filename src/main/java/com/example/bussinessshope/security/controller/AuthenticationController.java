package com.example.bussinessshope.security.controller;


import com.example.bussinessshope.security.dto.AuthenticationRequest;
import com.example.bussinessshope.security.dto.AuthenticationResponse;
import com.example.bussinessshope.security.dto.RegisterCustomerRequest;
import com.example.bussinessshope.security.dto.RegisterSellerRequest;
import com.example.bussinessshope.security.service.AuthenticationService;
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

    @PostMapping("/register/seller")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody RegisterSellerRequest request) {
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        return ResponseEntity.ok(authenticationService.registerCustomer(request));
    }

    @PostMapping("/authenticate/user")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}

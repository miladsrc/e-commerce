package com.example.bussinessshope.user.controller;


import com.example.bussinessshope.security.service.JwtService;
import com.example.bussinessshope.user.dto.UserRequestDTO;
import com.example.bussinessshope.user.dto.UserResponseDTO;
import com.example.bussinessshope.user.service.BaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final BaseUserService baseUserService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(baseUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(baseUserService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(baseUserService.createUser(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(baseUserService.updateUser(id, userRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        baseUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    // extracts token, then do the math
//    @GetMapping("/user/me")
//    public ResponseEntity<List<ContactResponseDTO>> getUserContacts(@RequestHeader("Authorization") String token) {
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//        Long usernameId = jwtService.extractUserId(token);
//        jwtService.extractClaim()
//        List<ContactResponseDTO> contacts = contactService.findUserContactByUserId(usernameId);
//        return ResponseEntity.ok(contacts);
//    }
}




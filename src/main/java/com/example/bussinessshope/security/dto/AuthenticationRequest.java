package com.example.bussinessshope.security.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "username cannot be empty.")
    private String username;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters.")
    private String password;
}

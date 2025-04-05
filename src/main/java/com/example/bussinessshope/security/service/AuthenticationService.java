package com.example.bussinessshope.security.service;


import com.example.bussinessshope.security.dto.AuthenticationRequest;
import com.example.bussinessshope.security.dto.AuthenticationResponse;
import com.example.bussinessshope.security.dto.RegisterCustomerRequest;
import com.example.bussinessshope.security.dto.RegisterSellerRequest;
import com.example.bussinessshope.security.statics.Role;
import com.example.bussinessshope.user.entity.UserEntity;
import com.example.bussinessshope.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse registerUser(RegisterSellerRequest registerRequest) {
        UserEntity userEntity = UserEntity
                .builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.SELLER)
                .build();
        userRepository.saveAndFlush(userEntity);
        var token = jwtService.generateToken(userEntity);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public AuthenticationResponse registerCustomer(RegisterCustomerRequest registerRequest) {
        UserEntity userEntity = UserEntity
                .builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.saveAndFlush(userEntity);
        var token = jwtService.generateToken(userEntity);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(
                   authenticationRequest.getEmail(),
                   authenticationRequest.getPassword()
           )
        );
        var user = userRepository.findUserByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("UserEntity not found"));

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}

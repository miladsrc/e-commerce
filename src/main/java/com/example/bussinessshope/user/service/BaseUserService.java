package com.example.bussinessshope.user.service;


import com.example.bussinessshope.user.dto.UserRequestDTO;
import com.example.bussinessshope.user.dto.UserResponseDTO;
import com.example.bussinessshope.user.entity.UserEntity;
import com.example.bussinessshope.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserEntity with ID " + id + " not found"));
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
        UserEntity userEntity = UserEntity.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .username(userRequestDTO.getUsername())
                .password(encodedPassword)
                .email(userRequestDTO.getEmail())
                .role(userRequestDTO.getRole())
                .build();
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return UserResponseDTO.builder()
                .id(savedUserEntity.getId())
                .firstName(savedUserEntity.getFirstName())
                .lastName(savedUserEntity.getLastName())
                .username(savedUserEntity.getUsername())
                .email(savedUserEntity.getEmail())
                .role(savedUserEntity.getRole())
                .build();
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        UserEntity oldUserEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserEntity with ID " + id + " not found"));

        String encodedPassword = (userRequestDTO.getPassword() != null) ?
                passwordEncoder.encode(userRequestDTO.getPassword()) : oldUserEntity.getPassword();

        UserEntity updatedUserEntity = UserEntity.builder()
                .id(oldUserEntity.getId())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .username(userRequestDTO.getUsername())
                .password(encodedPassword)
                .email(userRequestDTO.getEmail())
                .role(userRequestDTO.getRole())
                .build();

        UserEntity savedUserEntity = userRepository.save(updatedUserEntity);

        return UserResponseDTO.builder()
                .id(savedUserEntity.getId())
                .firstName(savedUserEntity.getFirstName())
                .lastName(savedUserEntity.getLastName())
                .username(savedUserEntity.getUsername())
                .email(savedUserEntity.getEmail())
                .role(savedUserEntity.getRole())
                .build();
    }


    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("UserEntity with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO mapToDTO(UserEntity userEntity) {
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }
}
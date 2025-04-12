package com.example.bussinessshope.config.service;

import com.example.bussinessshope.security.entity.UserPrincipal;
import com.example.bussinessshope.user.entity.UserEntity;
import com.example.bussinessshope.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserPrincipal(Math.toIntExact(user.getId()), user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}

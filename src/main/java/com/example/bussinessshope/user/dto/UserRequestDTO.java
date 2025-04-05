package com.example.bussinessshope.user.dto;

import com.example.bussinessshope.security.statics.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Role role;
}

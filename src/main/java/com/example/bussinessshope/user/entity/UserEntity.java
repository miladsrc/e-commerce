package com.example.bussinessshope.user.entity;

import com.example.bussinessshope.business.entity.BusinessEntity;
import com.example.bussinessshope.order.entity.OrderEntity;
import com.example.bussinessshope.security.statics.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SoftDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@SoftDelete
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "USERS")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    String lastName;

    @Column(name = "USERNAME", nullable = false, unique = true)
    String username;

    @Column(name = "PASSWORD", nullable = false)
    String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<BusinessEntity> businessList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderEntity> orderList;

    // USER DETAILS METHODS

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the role to a GrantedAuthority
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        // Define your logic; for now, return true (always valid)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Define your logic; for now, return true (always valid)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Define your logic; for now, return true (always valid)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Define your logic; for now, return true (always enabled)
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

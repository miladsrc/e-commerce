package com.example.bussinessshope.business.dto;

import com.example.bussinessshope.shared.entity.Address;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessCreateDto {

    @NotBlank(message = "Name can't be null or blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 to 50 characters")
    private String name;

    @NotBlank(message = "Email can't be null or blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number can't be null or blank")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and not contain any sign like +")
    private String phoneNumber;

    @NotNull(message = "Address list can't be null")
    @Size(min = 1, message = "At least one address is required")
    private List<Address> addressList;
}

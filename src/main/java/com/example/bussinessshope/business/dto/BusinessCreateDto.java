package com.example.bussinessshope.business.dto;

import com.example.bussinessshope.shared.entity.Address;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCreateDto {
    private String name;
    private String email;
    private String phoneNumber;
    private List<Address> addressList;
}

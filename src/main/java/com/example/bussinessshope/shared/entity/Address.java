package com.example.bussinessshope.shared.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Immutable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class Address {

    @Pattern(regexp = "^(?:\\S+\\s+){4,}\\S+$", message = "Address must contain at least 5 words")
    String address;

    @Pattern(regexp = "\\d{10,}", message = "ZipCode must contain at least 10 digits")
    String zipCode;

    @Pattern(regexp = "\\d{5,}", message = "Plate must contain at least 5 digits")
    String plate;
}
package com.example.bop.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class LocationDTO {
    private UUID id;

    @NotBlank(message = "Location name is required")
    private String name;

    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phone;
    private String email;
    private String timezone;
    private Boolean isActive;
}

package com.example.bop.tenant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TenantRegistrationRequest {

    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Industry type is required")
    private String industryType;

    @NotBlank(message = "Owner first name is required")
    private String ownerFirstName;

    @NotBlank(message = "Owner last name is required")
    private String ownerLastName;

    @NotBlank(message = "Owner email is required")
    @Email(message = "Invalid email format")
    private String ownerEmail;

    @NotBlank(message = "Password is required")
    private String ownerPassword;

    private String locationName;
    private String locationCity;
}

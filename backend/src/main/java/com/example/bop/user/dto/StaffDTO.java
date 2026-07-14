package com.example.bop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class StaffDTO {
    private UUID id;
    private UUID userId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    private String phone;
    private UUID roleId;
    private String roleName;
    private UUID locationId;
    private String locationName;
    private String specialization;
    private String bio;
    private String workingHours;
    private Boolean isAvailable;
    private Boolean isActive;
}

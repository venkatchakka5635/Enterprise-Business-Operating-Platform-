package com.example.bop.crm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CustomerDTO {
    private UUID id;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String leadStatus;
    private String source;
    private String tags;
    private String customFields;
    private Boolean consentSms;
    private Boolean consentEmail;
    private Boolean consentWhatsapp;
    private Boolean isActive;
}

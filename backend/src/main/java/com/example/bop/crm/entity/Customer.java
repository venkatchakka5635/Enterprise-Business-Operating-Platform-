package com.example.bop.crm.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    private String email;
    private String phone;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String gender;
    @Column(columnDefinition = "text")
    private String address;
    @Column(name = "lead_status")
    @Builder.Default
    private String leadStatus = "NEW";
    private String source;
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    private String tags = "[]";
    @Column(name = "custom_fields", columnDefinition = "jsonb")
    @Builder.Default
    private String customFields = "{}";
    @Column(name = "consent_sms")
    @Builder.Default
    private Boolean consentSms = false;
    @Column(name = "consent_email")
    @Builder.Default
    private Boolean consentEmail = true;
    @Column(name = "consent_whatsapp")
    @Builder.Default
    private Boolean consentWhatsapp = false;
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}

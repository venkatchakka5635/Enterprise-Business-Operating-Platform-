package com.example.bop.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TenantDTO {
    private UUID id;

    @NotBlank(message = "Tenant name is required")
    private String name;

    private String slug;
    private String industryType;
    private String logoUrl;
    private String themeColor;
    private Boolean isActive;
}

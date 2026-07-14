package com.example.bop.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ServiceDTO {
    private UUID id;
    @NotBlank(message = "Service name is required")
    private String name;
    private String description;
    private UUID categoryId;
    private String categoryName;
    private Integer durationMinutes;
    private BigDecimal price;
    private String currency;
    private Integer bufferMinutes;
    private UUID formSchemaId;
    private UUID workflowId;
    private String status;
    private Integer version;
    private Boolean isLatest;
    private List<UUID> staffIds;
}

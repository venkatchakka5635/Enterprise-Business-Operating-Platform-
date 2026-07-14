package com.example.bop.form.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FormSchemaDTO {
    private UUID id;
    @NotBlank(message = "Form name is required")
    private String name;
    private String description;
    private String fields; // JSON string
    private Integer version;
    private Boolean isActive;
}

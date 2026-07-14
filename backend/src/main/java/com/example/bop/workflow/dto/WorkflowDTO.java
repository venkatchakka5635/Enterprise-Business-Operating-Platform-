package com.example.bop.workflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WorkflowDTO {
    private UUID id;
    @NotBlank(message = "Workflow name is required")
    private String name;
    private String description;
    private String stages; // JSON array string
    private Boolean isActive;
}

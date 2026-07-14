package com.example.bop.workflow.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workflow_definitions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkflowDefinition extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(columnDefinition = "jsonb", nullable = false)
    @Builder.Default
    private String stages = "[]";
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}

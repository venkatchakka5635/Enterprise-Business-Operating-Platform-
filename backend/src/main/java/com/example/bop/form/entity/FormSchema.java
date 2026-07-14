package com.example.bop.form.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "form_schemas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FormSchema extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(columnDefinition = "jsonb", nullable = false)
    @Builder.Default
    private String fields = "[]";
    @Builder.Default
    private Integer version = 1;
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}

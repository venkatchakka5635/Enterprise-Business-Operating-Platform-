package com.example.bop.catalog.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceCategory extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}

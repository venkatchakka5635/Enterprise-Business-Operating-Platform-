package com.example.bop.catalog.entity;

import com.example.bop.common.entity.BaseEntity;
import com.example.bop.user.entity.Staff;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    @Builder.Default
    private Integer durationMinutes = 30;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Builder.Default
    private String currency = "INR";

    @Column(name = "buffer_minutes")
    @Builder.Default
    private Integer bufferMinutes = 0;

    @Column(name = "form_schema_id")
    private UUID formSchemaId;

    @Column(name = "workflow_id")
    private UUID workflowId;

    @Builder.Default
    private String status = "ACTIVE";

    @Builder.Default
    private Integer version = 1;

    @Column(name = "is_latest")
    @Builder.Default
    private Boolean isLatest = true;

    @Column(name = "parent_service_id")
    private UUID parentServiceId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "service_staff",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id"))
    @Builder.Default
    private Set<Staff> eligibleStaff = new HashSet<>();
}

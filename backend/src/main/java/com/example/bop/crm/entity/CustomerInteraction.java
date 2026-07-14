package com.example.bop.crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer_interactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    @Column(name = "interaction_type", nullable = false)
    private String interactionType;
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    @Column(name = "reference_type")
    private String referenceType;
    @Column(name = "reference_id")
    private UUID referenceId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private UUID createdBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

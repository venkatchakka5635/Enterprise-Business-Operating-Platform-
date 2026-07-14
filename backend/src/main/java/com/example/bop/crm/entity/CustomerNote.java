package com.example.bop.crm.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customer_notes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerNote extends BaseEntity {
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "is_follow_up")
    @Builder.Default
    private Boolean isFollowUp = false;
    @Column(name = "is_completed")
    @Builder.Default
    private Boolean isCompleted = false;
}

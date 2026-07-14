package com.example.bop.appointment.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment extends BaseEntity {

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Column(name = "service_version")
    @Builder.Default
    private Integer serviceVersion = 1;

    @Column(name = "staff_id")
    private UUID staffId;

    @Column(name = "location_id")
    private UUID locationId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Builder.Default
    private String status = "SCHEDULED";

    @Column(name = "current_stage")
    private String currentStage;

    @Column(name = "workflow_id")
    private UUID workflowId;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(name = "is_waitlisted")
    @Builder.Default
    private Boolean isWaitlisted = false;

    @Column(name = "cancellation_reason")
    private String cancellationReason;
}

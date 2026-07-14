package com.example.bop.appointment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AppointmentDTO {
    private UUID id;

    @NotNull(message = "Customer is required")
    private UUID customerId;
    private String customerName;

    @NotNull(message = "Service is required")
    private UUID serviceId;
    private String serviceName;
    private BigDecimal servicePrice;
    private Integer serviceVersion;

    private UUID staffId;
    private String staffName;

    private UUID locationId;
    private String locationName;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status;
    private String currentStage;
    private UUID workflowId;
    private String notes;
    private Boolean isWaitlisted;
    private String cancellationReason;
    private LocalDateTime createdAt;
}

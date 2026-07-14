package com.example.bop.form.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "form_submissions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FormSubmission extends BaseEntity {
    @Column(name = "form_schema_id", nullable = false)
    private UUID formSchemaId;
    @Column(name = "submitted_by")
    private UUID submittedBy;
    @Column(name = "customer_id")
    private UUID customerId;
    @Column(name = "appointment_id")
    private UUID appointmentId;
    @Column(columnDefinition = "jsonb", nullable = false)
    @Builder.Default
    private String data = "{}";
}

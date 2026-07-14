package com.example.bop.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;
    @Column(name = "recipient_id")
    private UUID recipientId;
    @Column(name = "recipient_email")
    private String recipientEmail;
    @Column(name = "recipient_phone")
    private String recipientPhone;
    @Column(nullable = false)
    private String channel;
    @Column(name = "template_name")
    private String templateName;
    private String subject;
    @Column(nullable = false, columnDefinition = "text")
    private String body;
    @Builder.Default
    private String status = "PENDING";
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    @Column(name = "failed_reason")
    private String failedReason;
    @Column(name = "reference_type")
    private String referenceType;
    @Column(name = "reference_id")
    private UUID referenceId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }
}

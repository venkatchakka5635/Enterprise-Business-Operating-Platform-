package com.example.bop.billing.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment extends BaseEntity {
    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(name = "payment_method")
    @Builder.Default
    private String paymentMethod = "CASH";
    @Column(name = "payment_reference")
    private String paymentReference;
    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;
    @Builder.Default
    private String status = "COMPLETED";
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    private String notes;
}

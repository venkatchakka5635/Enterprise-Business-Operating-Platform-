package com.example.bop.billing.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Invoice extends BaseEntity {
    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    @Column(name = "appointment_id")
    private UUID appointmentId;
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO;
    @Column(name = "tax_amount")
    @Builder.Default
    private BigDecimal taxAmount = BigDecimal.ZERO;
    @Column(name = "tax_rate")
    @Builder.Default
    private BigDecimal taxRate = BigDecimal.ZERO;
    @Column(name = "discount_amount")
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "amount_paid")
    @Builder.Default
    private BigDecimal amountPaid = BigDecimal.ZERO;
    @Builder.Default
    private String currency = "INR";
    @Builder.Default
    private String status = "DRAFT";
    @Column(name = "due_date")
    private LocalDate dueDate;
    private String notes;

    @OneToMany(mappedBy = "invoiceId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<InvoiceLineItem> lineItems = new ArrayList<>();
}

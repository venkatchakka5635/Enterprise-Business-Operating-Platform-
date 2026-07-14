package com.example.bop.billing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_line_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InvoiceLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;
    @Column(nullable = false)
    private String description;
    @Builder.Default
    private Integer quantity = 1;
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(name = "service_id")
    private UUID serviceId;
    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;
}

package com.example.bop.billing.repository;

import com.example.bop.billing.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByInvoiceId(UUID invoiceId);
}

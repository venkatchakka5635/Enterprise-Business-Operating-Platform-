package com.example.bop.billing.service;

import com.example.bop.billing.entity.Invoice;
import com.example.bop.billing.entity.InvoiceLineItem;
import com.example.bop.billing.entity.Payment;
import com.example.bop.billing.repository.InvoiceRepository;
import com.example.bop.billing.repository.PaymentRepository;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private static final AtomicLong invoiceCounter = new AtomicLong(System.currentTimeMillis() % 100000);

    public PagedResponse<Invoice> listInvoices(String status, Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        if (status != null && !status.isBlank()) {
            return PagedResponse.from(invoiceRepository.findByTenantIdAndStatus(tenantId, status, pageable));
        }
        return PagedResponse.from(invoiceRepository.findByTenantId(tenantId, pageable));
    }

    public Invoice getInvoice(UUID id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
    }

    @Transactional
    public Invoice createInvoice(UUID customerId, UUID appointmentId, List<InvoiceLineItem> items,
                                  BigDecimal taxRate, BigDecimal discountAmount) {
        UUID tenantId = TenantContext.getTenantId();
        String invoiceNumber = "INV-" + String.format("%06d", invoiceCounter.incrementAndGet());

        BigDecimal subtotal = items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tax = subtotal.multiply(taxRate != null ? taxRate : BigDecimal.ZERO).divide(BigDecimal.valueOf(100));
        BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        BigDecimal total = subtotal.add(tax).subtract(discount);

        Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceNumber)
                .customerId(customerId)
                .appointmentId(appointmentId)
                .subtotal(subtotal)
                .taxAmount(tax)
                .taxRate(taxRate != null ? taxRate : BigDecimal.ZERO)
                .discountAmount(discount)
                .total(total)
                .status("DRAFT")
                .dueDate(LocalDate.now().plusDays(30))
                .build();
        invoice.setTenantId(tenantId);
        invoice = invoiceRepository.save(invoice);

        UUID invoiceId = invoice.getId();
        for (int i = 0; i < items.size(); i++) {
            InvoiceLineItem item = items.get(i);
            item.setInvoiceId(invoiceId);
            item.setTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            item.setSortOrder(i);
        }
        invoice.setLineItems(items);
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Payment recordPayment(UUID invoiceId, BigDecimal amount, String method, String reference) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", invoiceId));

        Payment payment = Payment.builder()
                .invoiceId(invoiceId)
                .amount(amount)
                .paymentMethod(method != null ? method : "CASH")
                .paymentReference(reference)
                .status("COMPLETED")
                .paidAt(LocalDateTime.now())
                .build();
        payment.setTenantId(TenantContext.getTenantId());
        payment = paymentRepository.save(payment);

        invoice.setAmountPaid(invoice.getAmountPaid().add(amount));
        if (invoice.getAmountPaid().compareTo(invoice.getTotal()) >= 0) {
            invoice.setStatus("PAID");
        } else {
            invoice.setStatus("PARTIALLY_PAID");
        }
        invoiceRepository.save(invoice);

        return payment;
    }

    public List<Payment> getPaymentsForInvoice(UUID invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }
}

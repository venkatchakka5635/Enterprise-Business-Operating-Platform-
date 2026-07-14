package com.example.bop.billing.controller;

import com.example.bop.billing.entity.Invoice;
import com.example.bop.billing.entity.InvoiceLineItem;
import com.example.bop.billing.entity.Payment;
import com.example.bop.billing.service.BillingService;
import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
@Tag(name = "Billing & Payments")
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/invoices")
    @Operation(summary = "List invoices")
    public ApiResponse<PagedResponse<Invoice>> listInvoices(
            @RequestParam(required = false) String status,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.ok(billingService.listInvoices(status, pageable));
    }

    @GetMapping("/invoices/{id}")
    @Operation(summary = "Get invoice detail")
    public ApiResponse<Invoice> getInvoice(@PathVariable UUID id) {
        return ApiResponse.ok(billingService.getInvoice(id));
    }

    @PostMapping("/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'CASHIER')")
    @Operation(summary = "Create an invoice")
    @SuppressWarnings("unchecked")
    public ApiResponse<Invoice> createInvoice(@RequestBody Map<String, Object> body) {
        UUID customerId = UUID.fromString(body.get("customerId").toString());
        UUID appointmentId = body.containsKey("appointmentId") ? UUID.fromString(body.get("appointmentId").toString()) : null;
        BigDecimal taxRate = body.containsKey("taxRate") ? new BigDecimal(body.get("taxRate").toString()) : BigDecimal.ZERO;
        BigDecimal discount = body.containsKey("discountAmount") ? new BigDecimal(body.get("discountAmount").toString()) : BigDecimal.ZERO;

        List<Map<String, Object>> rawItems = (List<Map<String, Object>>) body.get("lineItems");
        List<InvoiceLineItem> items = rawItems.stream().map(ri -> InvoiceLineItem.builder()
                .description(ri.get("description").toString())
                .quantity(ri.containsKey("quantity") ? Integer.parseInt(ri.get("quantity").toString()) : 1)
                .unitPrice(new BigDecimal(ri.get("unitPrice").toString()))
                .total(BigDecimal.ZERO)
                .serviceId(ri.containsKey("serviceId") ? UUID.fromString(ri.get("serviceId").toString()) : null)
                .build()).collect(Collectors.toList());

        return ApiResponse.ok(billingService.createInvoice(customerId, appointmentId, items, taxRate, discount));
    }

    @PostMapping("/invoices/{id}/payments")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'CASHIER')")
    @Operation(summary = "Record a payment against an invoice")
    public ApiResponse<Payment> recordPayment(@PathVariable UUID id, @RequestBody Map<String, Object> body) {
        BigDecimal amount = new BigDecimal(body.get("amount").toString());
        String method = body.containsKey("method") ? body.get("method").toString() : "CASH";
        String reference = body.containsKey("reference") ? body.get("reference").toString() : null;
        return ApiResponse.ok(billingService.recordPayment(id, amount, method, reference));
    }

    @GetMapping("/invoices/{id}/payments")
    @Operation(summary = "List payments for an invoice")
    public ApiResponse<List<Payment>> getPayments(@PathVariable UUID id) {
        return ApiResponse.ok(billingService.getPaymentsForInvoice(id));
    }
}

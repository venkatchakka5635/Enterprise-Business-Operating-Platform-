package com.example.bop.dashboard.controller;

import com.example.bop.appointment.repository.AppointmentRepository;
import com.example.bop.billing.repository.InvoiceRepository;
import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.crm.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard & Reports")
public class DashboardController {

    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/stats")
    @Operation(summary = "Get dashboard summary stats")
    public ApiResponse<Map<String, Object>> getStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        UUID tenantId = TenantContext.getTenantId();

        if (from == null) from = LocalDate.now().withDayOfMonth(1);
        if (to == null) to = LocalDate.now().plusDays(1);

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        long totalAppointments = appointmentRepository.findByTenantIdAndDateRange(tenantId, start, end).size();
        BigDecimal revenue = invoiceRepository.sumRevenueByPeriod(tenantId, start, end);
        long totalCustomers = customerRepository.countByTenantId(tenantId);
        long totalInvoices = invoiceRepository.countByTenantId(tenantId);
        long paidInvoices = invoiceRepository.countByTenantIdAndStatus(tenantId, "PAID");

        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalAppointments", totalAppointments);
        stats.put("revenue", revenue);
        stats.put("totalCustomers", totalCustomers);
        stats.put("totalInvoices", totalInvoices);
        stats.put("paidInvoices", paidInvoices);
        stats.put("periodFrom", from);
        stats.put("periodTo", to);

        return ApiResponse.ok(stats);
    }

    @GetMapping("/revenue-chart")
    @Operation(summary = "Get daily revenue for chart")
    public ApiResponse<List<Map<String, Object>>> revenueChart(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        UUID tenantId = TenantContext.getTenantId();
        if (from == null) from = LocalDate.now().minusDays(30);
        if (to == null) to = LocalDate.now().plusDays(1);

        List<Map<String, Object>> data = new ArrayList<>();
        LocalDate cursor = from;
        while (!cursor.isAfter(to)) {
            BigDecimal dayRevenue = invoiceRepository.sumRevenueByPeriod(tenantId,
                    cursor.atStartOfDay(), cursor.plusDays(1).atStartOfDay());
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("date", cursor.toString());
            point.put("revenue", dayRevenue);
            data.add(point);
            cursor = cursor.plusDays(1);
        }
        return ApiResponse.ok(data);
    }
}

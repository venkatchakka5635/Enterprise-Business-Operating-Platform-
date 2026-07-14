package com.example.bop.appointment.controller;

import com.example.bop.appointment.dto.AppointmentDTO;
import com.example.bop.appointment.service.AppointmentService;
import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    @Operation(summary = "List appointments")
    public ApiResponse<PagedResponse<AppointmentDTO>> list(
            @PageableDefault(sort = "startTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.ok(appointmentService.listAppointments(pageable));
    }

    @GetMapping("/calendar")
    @Operation(summary = "Get appointments for a date range")
    public ApiResponse<List<AppointmentDTO>> calendar(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ApiResponse.ok(appointmentService.getAppointmentsByDateRange(start, end));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment detail")
    public ApiResponse<AppointmentDTO> get(@PathVariable UUID id) {
        return ApiResponse.ok(appointmentService.getAppointment(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Book an appointment")
    public ApiResponse<AppointmentDTO> create(@Valid @RequestBody AppointmentDTO dto) {
        return ApiResponse.ok(appointmentService.createAppointment(dto), "Appointment booked");
    }

    @PatchMapping("/{id}/reschedule")
    @Operation(summary = "Reschedule an appointment")
    public ApiResponse<AppointmentDTO> reschedule(@PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        LocalDateTime newTime = LocalDateTime.parse(body.get("startTime"));
        return ApiResponse.ok(appointmentService.reschedule(id, newTime));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel an appointment")
    public ApiResponse<AppointmentDTO> cancel(@PathVariable UUID id,
            @RequestBody(required = false) Map<String, String> body) {
        String reason = body != null ? body.get("reason") : null;
        return ApiResponse.ok(appointmentService.cancel(id, reason));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'PROVIDER', 'RECEPTION')")
    @Operation(summary = "Update appointment status")
    public ApiResponse<AppointmentDTO> updateStatus(@PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        return ApiResponse.ok(appointmentService.updateStatus(id, body.get("status")));
    }

    @PatchMapping("/{id}/stage")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'PROVIDER', 'RECEPTION')")
    @Operation(summary = "Transition appointment to next workflow stage")
    public ApiResponse<AppointmentDTO> transitionStage(@PathVariable UUID id,
            @RequestBody Map<String, String> body) {
        return ApiResponse.ok(appointmentService.transitionStage(id, body.get("stage")));
    }
}

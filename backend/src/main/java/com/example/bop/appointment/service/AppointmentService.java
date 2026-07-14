package com.example.bop.appointment.service;

import com.example.bop.appointment.dto.AppointmentDTO;
import com.example.bop.appointment.entity.Appointment;
import com.example.bop.appointment.repository.AppointmentRepository;
import com.example.bop.catalog.entity.ServiceEntity;
import com.example.bop.catalog.repository.ServiceRepository;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ConflictException;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.crm.entity.Customer;
import com.example.bop.crm.repository.CustomerRepository;
import com.example.bop.user.entity.Staff;
import com.example.bop.user.repository.StaffRepository;
import com.example.bop.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;
    private final WorkflowService workflowService;

    public PagedResponse<AppointmentDTO> listAppointments(Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        return PagedResponse.from(appointmentRepository.findByTenantId(tenantId, pageable).map(this::toDTO));
    }

    public AppointmentDTO getAppointment(UUID id) {
        Appointment apt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        return toDTO(apt);
    }

    public List<AppointmentDTO> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        UUID tenantId = TenantContext.getTenantId();
        return appointmentRepository.findByTenantIdAndDateRange(tenantId, start, end)
                .stream().map(this::toDTO).toList();
    }

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        UUID tenantId = TenantContext.getTenantId();

        ServiceEntity svc = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", dto.getServiceId()));

        LocalDateTime endTime = dto.getStartTime().plusMinutes(svc.getDurationMinutes() + svc.getBufferMinutes());

        // Conflict detection
        if (dto.getStaffId() != null) {
            List<Appointment> conflicts = appointmentRepository.findConflicts(
                    tenantId, dto.getStaffId(), dto.getStartTime(), endTime);
            if (!conflicts.isEmpty()) {
                throw new ConflictException("Staff member has a conflicting appointment at this time");
            }
        }

        Appointment apt = Appointment.builder()
                .customerId(dto.getCustomerId())
                .serviceId(svc.getId())
                .serviceVersion(svc.getVersion())
                .staffId(dto.getStaffId())
                .locationId(dto.getLocationId())
                .startTime(dto.getStartTime())
                .endTime(endTime)
                .status("SCHEDULED")
                .workflowId(svc.getWorkflowId())
                .notes(dto.getNotes())
                .isWaitlisted(false)
                .build();
        apt.setTenantId(tenantId);

        return toDTO(appointmentRepository.save(apt));
    }

    @Transactional
    public AppointmentDTO reschedule(UUID id, LocalDateTime newStartTime) {
        Appointment apt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        ServiceEntity svc = serviceRepository.findById(apt.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", apt.getServiceId()));

        LocalDateTime newEndTime = newStartTime.plusMinutes(svc.getDurationMinutes() + svc.getBufferMinutes());

        if (apt.getStaffId() != null) {
            UUID tenantId = TenantContext.getTenantId();
            List<Appointment> conflicts = appointmentRepository.findConflicts(
                    tenantId, apt.getStaffId(), newStartTime, newEndTime);
            conflicts.removeIf(a -> a.getId().equals(id));
            if (!conflicts.isEmpty()) {
                throw new ConflictException("Staff member has a conflicting appointment at the new time");
            }
        }

        apt.setStartTime(newStartTime);
        apt.setEndTime(newEndTime);
        apt.setStatus("SCHEDULED");
        return toDTO(appointmentRepository.save(apt));
    }

    @Transactional
    public AppointmentDTO cancel(UUID id, String reason) {
        Appointment apt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        apt.setStatus("CANCELLED");
        apt.setCancellationReason(reason);
        return toDTO(appointmentRepository.save(apt));
    }

    @Transactional
    public AppointmentDTO updateStatus(UUID id, String status) {
        Appointment apt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        apt.setStatus(status);
        return toDTO(appointmentRepository.save(apt));
    }

    @Transactional
    public AppointmentDTO transitionStage(UUID id, String targetStage) {
        Appointment apt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        if (apt.getWorkflowId() == null) {
            throw new IllegalArgumentException("This appointment has no workflow assigned");
        }

        String newStage = workflowService.validateTransition(apt.getWorkflowId(), apt.getCurrentStage(), targetStage);
        apt.setCurrentStage(newStage);
        apt.setStatus("IN_PROGRESS");
        return toDTO(appointmentRepository.save(apt));
    }

    private AppointmentDTO toDTO(Appointment a) {
        AppointmentDTO.AppointmentDTOBuilder builder = AppointmentDTO.builder()
                .id(a.getId())
                .customerId(a.getCustomerId())
                .serviceId(a.getServiceId())
                .serviceVersion(a.getServiceVersion())
                .staffId(a.getStaffId())
                .locationId(a.getLocationId())
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .status(a.getStatus())
                .currentStage(a.getCurrentStage())
                .workflowId(a.getWorkflowId())
                .notes(a.getNotes())
                .isWaitlisted(a.getIsWaitlisted())
                .cancellationReason(a.getCancellationReason())
                .createdAt(a.getCreatedAt());

        // Resolve names
        try {
            customerRepository.findById(a.getCustomerId())
                    .ifPresent(c -> builder.customerName(c.getFirstName() + " " + c.getLastName()));
            if (a.getServiceId() != null) {
                serviceRepository.findById(a.getServiceId())
                        .ifPresent(s -> { builder.serviceName(s.getName()); builder.servicePrice(s.getPrice()); });
            }
            if (a.getStaffId() != null) {
                staffRepository.findById(a.getStaffId())
                        .ifPresent(s -> builder.staffName(s.getUser().getFirstName() + " " + s.getUser().getLastName()));
            }
        } catch (Exception ignored) {}

        return builder.build();
    }
}

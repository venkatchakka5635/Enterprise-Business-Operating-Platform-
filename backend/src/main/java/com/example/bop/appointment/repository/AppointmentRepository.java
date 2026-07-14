package com.example.bop.appointment.repository;

import com.example.bop.appointment.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Page<Appointment> findByTenantId(UUID tenantId, Pageable pageable);

    Page<Appointment> findByTenantIdAndStatus(UUID tenantId, String status, Pageable pageable);

    List<Appointment> findByTenantIdAndStaffIdAndStartTimeBetweenAndStatusNot(
            UUID tenantId, UUID staffId, LocalDateTime start, LocalDateTime end, String excludeStatus);

    List<Appointment> findByCustomerId(UUID customerId);

    @Query("SELECT a FROM Appointment a WHERE a.tenantId = :tenantId AND a.staffId = :staffId " +
           "AND a.startTime < :endTime AND a.endTime > :startTime AND a.status != 'CANCELLED'")
    List<Appointment> findConflicts(UUID tenantId, UUID staffId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT a FROM Appointment a WHERE a.tenantId = :tenantId " +
           "AND a.startTime >= :start AND a.startTime < :end AND a.status != 'CANCELLED'")
    List<Appointment> findByTenantIdAndDateRange(UUID tenantId, LocalDateTime start, LocalDateTime end);

    Page<Appointment> findByTenantIdAndCustomerId(UUID tenantId, UUID customerId, Pageable pageable);
}

package com.example.bop.form.repository;

import com.example.bop.form.entity.FormSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, UUID> {
    Page<FormSubmission> findByTenantIdAndFormSchemaId(UUID tenantId, UUID formSchemaId, Pageable pageable);
    List<FormSubmission> findByCustomerId(UUID customerId);
    List<FormSubmission> findByAppointmentId(UUID appointmentId);
}

package com.example.bop.form.repository;

import com.example.bop.form.entity.FormSchema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FormSchemaRepository extends JpaRepository<FormSchema, UUID> {
    Page<FormSchema> findByTenantId(UUID tenantId, Pageable pageable);
}

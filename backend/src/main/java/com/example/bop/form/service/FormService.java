package com.example.bop.form.service;

import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.form.dto.FormSchemaDTO;
import com.example.bop.form.entity.FormSchema;
import com.example.bop.form.entity.FormSubmission;
import com.example.bop.form.repository.FormSchemaRepository;
import com.example.bop.form.repository.FormSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormService {

    private final FormSchemaRepository schemaRepository;
    private final FormSubmissionRepository submissionRepository;

    public PagedResponse<FormSchemaDTO> listSchemas(Pageable pageable) {
        return PagedResponse.from(
                schemaRepository.findByTenantId(TenantContext.getTenantId(), pageable).map(this::toDTO));
    }

    public FormSchemaDTO getSchema(UUID id) {
        FormSchema schema = schemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FormSchema", "id", id));
        return toDTO(schema);
    }

    @Transactional
    public FormSchemaDTO createSchema(FormSchemaDTO dto) {
        FormSchema schema = FormSchema.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .fields(dto.getFields() != null ? dto.getFields() : "[]")
                .build();
        schema.setTenantId(TenantContext.getTenantId());
        return toDTO(schemaRepository.save(schema));
    }

    @Transactional
    public FormSchemaDTO updateSchema(UUID id, FormSchemaDTO dto) {
        FormSchema schema = schemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FormSchema", "id", id));
        if (dto.getName() != null) schema.setName(dto.getName());
        if (dto.getDescription() != null) schema.setDescription(dto.getDescription());
        if (dto.getFields() != null) schema.setFields(dto.getFields());
        if (dto.getIsActive() != null) schema.setIsActive(dto.getIsActive());
        return toDTO(schemaRepository.save(schema));
    }

    @Transactional
    public FormSubmission submitForm(UUID schemaId, String data, UUID customerId, UUID appointmentId) {
        schemaRepository.findById(schemaId)
                .orElseThrow(() -> new ResourceNotFoundException("FormSchema", "id", schemaId));
        FormSubmission submission = FormSubmission.builder()
                .formSchemaId(schemaId)
                .data(data)
                .customerId(customerId)
                .appointmentId(appointmentId)
                .submittedBy(TenantContext.getUserId())
                .build();
        submission.setTenantId(TenantContext.getTenantId());
        return submissionRepository.save(submission);
    }

    public PagedResponse<FormSubmission> listSubmissions(UUID schemaId, Pageable pageable) {
        return PagedResponse.from(
                submissionRepository.findByTenantIdAndFormSchemaId(TenantContext.getTenantId(), schemaId, pageable));
    }

    private FormSchemaDTO toDTO(FormSchema s) {
        return FormSchemaDTO.builder()
                .id(s.getId()).name(s.getName()).description(s.getDescription())
                .fields(s.getFields()).version(s.getVersion()).isActive(s.getIsActive())
                .build();
    }
}

package com.example.bop.form.controller;

import com.example.bop.common.dto.ApiResponse;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.form.dto.FormSchemaDTO;
import com.example.bop.form.entity.FormSubmission;
import com.example.bop.form.service.FormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
@Tag(name = "Dynamic Forms")
public class FormController {

    private final FormService formService;

    @GetMapping
    @Operation(summary = "List form schemas")
    public ApiResponse<PagedResponse<FormSchemaDTO>> list(Pageable pageable) {
        return ApiResponse.ok(formService.listSchemas(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get form schema detail")
    public ApiResponse<FormSchemaDTO> get(@PathVariable UUID id) {
        return ApiResponse.ok(formService.getSchema(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Create form schema")
    public ApiResponse<FormSchemaDTO> create(@Valid @RequestBody FormSchemaDTO dto) {
        return ApiResponse.ok(formService.createSchema(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    @Operation(summary = "Update form schema")
    public ApiResponse<FormSchemaDTO> update(@PathVariable UUID id, @Valid @RequestBody FormSchemaDTO dto) {
        return ApiResponse.ok(formService.updateSchema(id, dto));
    }

    @PostMapping("/{schemaId}/submissions")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Submit a form")
    public ApiResponse<FormSubmission> submit(@PathVariable UUID schemaId, @RequestBody Map<String, Object> body) {
        String data = body.containsKey("data") ? body.get("data").toString() : "{}";
        UUID customerId = body.containsKey("customerId") ? UUID.fromString(body.get("customerId").toString()) : null;
        UUID appointmentId = body.containsKey("appointmentId") ? UUID.fromString(body.get("appointmentId").toString()) : null;
        return ApiResponse.ok(formService.submitForm(schemaId, data, customerId, appointmentId));
    }

    @GetMapping("/{schemaId}/submissions")
    @Operation(summary = "List submissions for a form schema")
    public ApiResponse<PagedResponse<FormSubmission>> listSubmissions(@PathVariable UUID schemaId, Pageable pageable) {
        return ApiResponse.ok(formService.listSubmissions(schemaId, pageable));
    }
}

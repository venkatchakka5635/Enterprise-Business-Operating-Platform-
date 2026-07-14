package com.example.bop.catalog.service;

import com.example.bop.catalog.dto.ServiceDTO;
import com.example.bop.catalog.entity.ServiceCategory;
import com.example.bop.catalog.entity.ServiceEntity;
import com.example.bop.catalog.repository.ServiceCategoryRepository;
import com.example.bop.catalog.repository.ServiceRepository;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.user.entity.Staff;
import com.example.bop.user.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final StaffRepository staffRepository;

    public PagedResponse<ServiceDTO> listServices(Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        return PagedResponse.from(
                serviceRepository.findByTenantIdAndIsLatest(tenantId, true, pageable)
                        .map(this::toDTO));
    }

    public ServiceDTO getService(UUID serviceId) {
        ServiceEntity svc = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", serviceId));
        return toDTO(svc);
    }

    @Transactional
    public ServiceDTO createService(ServiceDTO dto) {
        UUID tenantId = TenantContext.getTenantId();
        ServiceEntity svc = ServiceEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .durationMinutes(dto.getDurationMinutes() != null ? dto.getDurationMinutes() : 30)
                .price(dto.getPrice() != null ? dto.getPrice() : java.math.BigDecimal.ZERO)
                .currency(dto.getCurrency() != null ? dto.getCurrency() : "INR")
                .bufferMinutes(dto.getBufferMinutes() != null ? dto.getBufferMinutes() : 0)
                .formSchemaId(dto.getFormSchemaId())
                .workflowId(dto.getWorkflowId())
                .status("ACTIVE")
                .version(1)
                .isLatest(true)
                .build();
        svc.setTenantId(tenantId);

        if (dto.getCategoryId() != null) {
            svc.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        }
        if (dto.getStaffIds() != null && !dto.getStaffIds().isEmpty()) {
            Set<Staff> staff = new HashSet<>(staffRepository.findByTenantIdAndIdIn(tenantId, dto.getStaffIds()));
            svc.setEligibleStaff(staff);
        }
        return toDTO(serviceRepository.save(svc));
    }

    @Transactional
    public ServiceDTO updateService(UUID serviceId, ServiceDTO dto) {
        ServiceEntity existing = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", serviceId));

        // Create new version
        existing.setIsLatest(false);
        serviceRepository.save(existing);

        UUID tenantId = TenantContext.getTenantId();
        ServiceEntity newVersion = ServiceEntity.builder()
                .name(dto.getName() != null ? dto.getName() : existing.getName())
                .description(dto.getDescription() != null ? dto.getDescription() : existing.getDescription())
                .durationMinutes(dto.getDurationMinutes() != null ? dto.getDurationMinutes() : existing.getDurationMinutes())
                .price(dto.getPrice() != null ? dto.getPrice() : existing.getPrice())
                .currency(dto.getCurrency() != null ? dto.getCurrency() : existing.getCurrency())
                .bufferMinutes(dto.getBufferMinutes() != null ? dto.getBufferMinutes() : existing.getBufferMinutes())
                .formSchemaId(dto.getFormSchemaId() != null ? dto.getFormSchemaId() : existing.getFormSchemaId())
                .workflowId(dto.getWorkflowId() != null ? dto.getWorkflowId() : existing.getWorkflowId())
                .status(existing.getStatus())
                .version(existing.getVersion() + 1)
                .isLatest(true)
                .parentServiceId(existing.getParentServiceId() != null ? existing.getParentServiceId() : existing.getId())
                .category(dto.getCategoryId() != null ? categoryRepository.findById(dto.getCategoryId()).orElse(null) : existing.getCategory())
                .build();
        newVersion.setTenantId(tenantId);

        if (dto.getStaffIds() != null) {
            Set<Staff> staff = new HashSet<>(staffRepository.findByTenantIdAndIdIn(tenantId, dto.getStaffIds()));
            newVersion.setEligibleStaff(staff);
        } else {
            newVersion.setEligibleStaff(new HashSet<>(existing.getEligibleStaff()));
        }

        return toDTO(serviceRepository.save(newVersion));
    }

    // -- Categories --
    public List<ServiceCategory> listCategories() {
        return categoryRepository.findByTenantId(TenantContext.getTenantId(), Pageable.unpaged()).getContent();
    }

    @Transactional
    public ServiceCategory createCategory(String name, String description) {
        ServiceCategory cat = ServiceCategory.builder().name(name).description(description).build();
        cat.setTenantId(TenantContext.getTenantId());
        return categoryRepository.save(cat);
    }

    private ServiceDTO toDTO(ServiceEntity s) {
        return ServiceDTO.builder()
                .id(s.getId()).name(s.getName()).description(s.getDescription())
                .categoryId(s.getCategory() != null ? s.getCategory().getId() : null)
                .categoryName(s.getCategory() != null ? s.getCategory().getName() : null)
                .durationMinutes(s.getDurationMinutes()).price(s.getPrice())
                .currency(s.getCurrency()).bufferMinutes(s.getBufferMinutes())
                .formSchemaId(s.getFormSchemaId()).workflowId(s.getWorkflowId())
                .status(s.getStatus()).version(s.getVersion()).isLatest(s.getIsLatest())
                .staffIds(s.getEligibleStaff().stream().map(Staff::getId).collect(Collectors.toList()))
                .build();
    }
}

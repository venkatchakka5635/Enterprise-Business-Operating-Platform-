package com.example.bop.crm.service;

import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.crm.dto.CustomerDTO;
import com.example.bop.crm.entity.Customer;
import com.example.bop.crm.entity.CustomerInteraction;
import com.example.bop.crm.entity.CustomerNote;
import com.example.bop.crm.repository.CustomerInteractionRepository;
import com.example.bop.crm.repository.CustomerNoteRepository;
import com.example.bop.crm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrmService {

    private final CustomerRepository customerRepository;
    private final CustomerNoteRepository noteRepository;
    private final CustomerInteractionRepository interactionRepository;

    public PagedResponse<CustomerDTO> listCustomers(String search, String leadStatus, Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        if (search != null && !search.isBlank()) {
            return PagedResponse.from(customerRepository.search(tenantId, search, pageable).map(this::toDTO));
        }
        if (leadStatus != null && !leadStatus.isBlank()) {
            return PagedResponse.from(customerRepository.findByTenantIdAndLeadStatus(tenantId, leadStatus, pageable).map(this::toDTO));
        }
        return PagedResponse.from(customerRepository.findByTenantId(tenantId, pageable).map(this::toDTO));
    }

    public CustomerDTO getCustomer(UUID id) {
        return toDTO(customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id)));
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer c = Customer.builder()
                .firstName(dto.getFirstName()).lastName(dto.getLastName())
                .email(dto.getEmail()).phone(dto.getPhone())
                .dateOfBirth(dto.getDateOfBirth()).gender(dto.getGender())
                .address(dto.getAddress()).leadStatus(dto.getLeadStatus() != null ? dto.getLeadStatus() : "NEW")
                .source(dto.getSource()).tags(dto.getTags() != null ? dto.getTags() : "[]")
                .customFields(dto.getCustomFields() != null ? dto.getCustomFields() : "{}")
                .consentSms(dto.getConsentSms()).consentEmail(dto.getConsentEmail())
                .consentWhatsapp(dto.getConsentWhatsapp())
                .build();
        c.setTenantId(TenantContext.getTenantId());
        return toDTO(customerRepository.save(c));
    }

    @Transactional
    public CustomerDTO updateCustomer(UUID id, CustomerDTO dto) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        if (dto.getFirstName() != null) c.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) c.setLastName(dto.getLastName());
        if (dto.getEmail() != null) c.setEmail(dto.getEmail());
        if (dto.getPhone() != null) c.setPhone(dto.getPhone());
        if (dto.getDateOfBirth() != null) c.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) c.setGender(dto.getGender());
        if (dto.getAddress() != null) c.setAddress(dto.getAddress());
        if (dto.getLeadStatus() != null) c.setLeadStatus(dto.getLeadStatus());
        if (dto.getSource() != null) c.setSource(dto.getSource());
        if (dto.getConsentSms() != null) c.setConsentSms(dto.getConsentSms());
        if (dto.getConsentEmail() != null) c.setConsentEmail(dto.getConsentEmail());
        if (dto.getConsentWhatsapp() != null) c.setConsentWhatsapp(dto.getConsentWhatsapp());
        return toDTO(customerRepository.save(c));
    }

    // -- Notes --
    public List<CustomerNote> getNotes(UUID customerId) {
        return noteRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    @Transactional
    public CustomerNote addNote(UUID customerId, String content, java.time.LocalDate dueDate, boolean isFollowUp) {
        CustomerNote note = CustomerNote.builder()
                .customerId(customerId).content(content)
                .dueDate(dueDate).isFollowUp(isFollowUp)
                .build();
        note.setTenantId(TenantContext.getTenantId());
        return noteRepository.save(note);
    }

    // -- Interactions --
    public List<CustomerInteraction> getInteractions(UUID customerId) {
        return interactionRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    public void logInteraction(UUID customerId, String type, String description, String refType, UUID refId) {
        CustomerInteraction i = CustomerInteraction.builder()
                .tenantId(TenantContext.getTenantId())
                .customerId(customerId).interactionType(type)
                .description(description).referenceType(refType).referenceId(refId)
                .createdBy(TenantContext.getUserId())
                .build();
        interactionRepository.save(i);
    }

    private CustomerDTO toDTO(Customer c) {
        return CustomerDTO.builder()
                .id(c.getId()).firstName(c.getFirstName()).lastName(c.getLastName())
                .email(c.getEmail()).phone(c.getPhone()).dateOfBirth(c.getDateOfBirth())
                .gender(c.getGender()).address(c.getAddress()).leadStatus(c.getLeadStatus())
                .source(c.getSource()).tags(c.getTags()).customFields(c.getCustomFields())
                .consentSms(c.getConsentSms()).consentEmail(c.getConsentEmail())
                .consentWhatsapp(c.getConsentWhatsapp()).isActive(c.getIsActive())
                .build();
    }
}

package com.example.bop.tenant.service;

import com.example.bop.auth.JwtService;
import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ConflictException;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.tenant.dto.*;
import com.example.bop.tenant.entity.Location;
import com.example.bop.tenant.entity.Tenant;
import com.example.bop.tenant.repository.LocationRepository;
import com.example.bop.tenant.repository.TenantRepository;
import com.example.bop.user.entity.Role;
import com.example.bop.user.entity.User;
import com.example.bop.user.repository.RoleRepository;
import com.example.bop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final LocationRepository locationRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public Map<String, Object> registerTenant(TenantRegistrationRequest request) {
        String slug = generateSlug(request.getBusinessName());
        if (tenantRepository.existsBySlug(slug)) {
            throw new ConflictException("A business with a similar name already exists");
        }

        // Create tenant
        Tenant tenant = Tenant.builder()
                .name(request.getBusinessName())
                .slug(slug)
                .industryType(request.getIndustryType())
                .build();
        tenant = tenantRepository.save(tenant);

        // Create default roles
        List<Role> defaultRoles = createDefaultRoles(tenant.getId());

        // Find Owner role
        Role ownerRole = defaultRoles.stream()
                .filter(r -> "OWNER".equals(r.getName()))
                .findFirst()
                .orElseThrow();

        // Create owner user
        User owner = User.builder()
                .email(request.getOwnerEmail())
                .passwordHash(passwordEncoder.encode(request.getOwnerPassword()))
                .firstName(request.getOwnerFirstName())
                .lastName(request.getOwnerLastName())
                .role(ownerRole)
                .isActive(true)
                .build();
        owner.setTenantId(tenant.getId());
        owner.setCreatedAt(java.time.LocalDateTime.now());
        owner.setUpdatedAt(java.time.LocalDateTime.now());
        owner = userRepository.save(owner);

        // Create default location if provided
        if (request.getLocationName() != null && !request.getLocationName().isBlank()) {
            Location location = Location.builder()
                    .name(request.getLocationName())
                    .city(request.getLocationCity())
                    .build();
            location.setTenantId(tenant.getId());
            locationRepository.save(location);
        }

        // Generate token
        String token = jwtService.generateAccessToken(owner.getId(), tenant.getId(), owner.getEmail(), ownerRole.getName());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("token", token);
        result.put("tenantId", tenant.getId());
        result.put("userId", owner.getId());
        return result;
    }

    public TenantDTO getTenant(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", tenantId));
        return toDTO(tenant);
    }

    @Transactional
    public TenantDTO updateTenant(UUID tenantId, TenantDTO dto) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", tenantId));
        if (dto.getName() != null) tenant.setName(dto.getName());
        if (dto.getLogoUrl() != null) tenant.setLogoUrl(dto.getLogoUrl());
        if (dto.getThemeColor() != null) tenant.setThemeColor(dto.getThemeColor());
        if (dto.getIndustryType() != null) tenant.setIndustryType(dto.getIndustryType());
        return toDTO(tenantRepository.save(tenant));
    }

    // --- Locations ---

    public PagedResponse<LocationDTO> getLocations(Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        return PagedResponse.from(locationRepository.findByTenantId(tenantId, pageable).map(this::toLocationDTO));
    }

    @Transactional
    public LocationDTO createLocation(LocationDTO dto) {
        Location location = Location.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .zipCode(dto.getZipCode())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .timezone(dto.getTimezone() != null ? dto.getTimezone() : "Asia/Kolkata")
                .build();
        return toLocationDTO(locationRepository.save(location));
    }

    @Transactional
    public LocationDTO updateLocation(UUID locationId, LocationDTO dto) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", locationId));
        if (dto.getName() != null) location.setName(dto.getName());
        if (dto.getAddress() != null) location.setAddress(dto.getAddress());
        if (dto.getCity() != null) location.setCity(dto.getCity());
        if (dto.getState() != null) location.setState(dto.getState());
        if (dto.getCountry() != null) location.setCountry(dto.getCountry());
        if (dto.getZipCode() != null) location.setZipCode(dto.getZipCode());
        if (dto.getPhone() != null) location.setPhone(dto.getPhone());
        if (dto.getEmail() != null) location.setEmail(dto.getEmail());
        if (dto.getTimezone() != null) location.setTimezone(dto.getTimezone());
        if (dto.getIsActive() != null) location.setIsActive(dto.getIsActive());
        return toLocationDTO(locationRepository.save(location));
    }

    // --- Helpers ---

    private List<Role> createDefaultRoles(UUID tenantId) {
        List<Role> roles = new ArrayList<>();
        String[][] systemRoles = {
                {"OWNER", "Owner"},
                {"MANAGER", "Manager"},
                {"PROVIDER", "Provider"},
                {"RECEPTION", "Receptionist"},
                {"CASHIER", "Cashier"}
        };
        for (String[] r : systemRoles) {
            Role role = Role.builder()
                    .name(r[0])
                    .displayName(r[1])
                    .isSystem(true)
                    .build();
            role.setTenantId(tenantId);
            role.setCreatedAt(java.time.LocalDateTime.now());
            role.setUpdatedAt(java.time.LocalDateTime.now());
            roles.add(roleRepository.save(role));
        }
        return roles;
    }

    private String generateSlug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    private TenantDTO toDTO(Tenant t) {
        return TenantDTO.builder()
                .id(t.getId()).name(t.getName()).slug(t.getSlug())
                .industryType(t.getIndustryType()).logoUrl(t.getLogoUrl())
                .themeColor(t.getThemeColor()).isActive(t.getIsActive())
                .build();
    }

    private LocationDTO toLocationDTO(Location l) {
        return LocationDTO.builder()
                .id(l.getId()).name(l.getName()).address(l.getAddress())
                .city(l.getCity()).state(l.getState()).country(l.getCountry())
                .zipCode(l.getZipCode()).phone(l.getPhone()).email(l.getEmail())
                .timezone(l.getTimezone()).isActive(l.getIsActive())
                .build();
    }
}

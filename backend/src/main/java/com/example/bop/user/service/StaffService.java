package com.example.bop.user.service;

import com.example.bop.common.dto.PagedResponse;
import com.example.bop.common.exception.ConflictException;
import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.common.tenant.TenantContext;
import com.example.bop.user.dto.CreateStaffRequest;
import com.example.bop.user.dto.StaffDTO;
import com.example.bop.user.entity.Role;
import com.example.bop.user.entity.Staff;
import com.example.bop.user.entity.User;
import com.example.bop.user.repository.RoleRepository;
import com.example.bop.user.repository.StaffRepository;
import com.example.bop.user.repository.UserRepository;
import com.example.bop.tenant.entity.Location;
import com.example.bop.tenant.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;

    public PagedResponse<StaffDTO> listStaff(Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        return PagedResponse.from(staffRepository.findByTenantId(tenantId, pageable).map(this::toDTO));
    }

    public StaffDTO getStaff(UUID staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", staffId));
        return toDTO(staff);
    }

    @Transactional
    public StaffDTO createStaff(CreateStaffRequest request) {
        UUID tenantId = TenantContext.getTenantId();

        if (userRepository.existsByTenantIdAndEmail(tenantId, request.getEmail())) {
            throw new ConflictException("A user with this email already exists");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", request.getRoleId()));

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .role(role)
                .isActive(true)
                .build();
        user.setTenantId(tenantId);
        user = userRepository.save(user);

        Location location = null;
        if (request.getLocationId() != null) {
            location = locationRepository.findById(request.getLocationId()).orElse(null);
        }

        Staff staff = Staff.builder()
                .user(user)
                .location(location)
                .specialization(request.getSpecialization())
                .bio(request.getBio())
                .workingHours(request.getWorkingHours() != null ? request.getWorkingHours() : "{}")
                .isAvailable(true)
                .build();
        staff.setTenantId(tenantId);
        staff = staffRepository.save(staff);

        return toDTO(staff);
    }

    @Transactional
    public StaffDTO updateStaff(UUID staffId, StaffDTO dto) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", staffId));
        User user = staff.getUser();

        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", dto.getRoleId()));
            user.setRole(role);
        }
        if (dto.getIsActive() != null) user.setIsActive(dto.getIsActive());
        userRepository.save(user);

        if (dto.getSpecialization() != null) staff.setSpecialization(dto.getSpecialization());
        if (dto.getBio() != null) staff.setBio(dto.getBio());
        if (dto.getWorkingHours() != null) staff.setWorkingHours(dto.getWorkingHours());
        if (dto.getIsAvailable() != null) staff.setIsAvailable(dto.getIsAvailable());
        if (dto.getLocationId() != null) {
            Location loc = locationRepository.findById(dto.getLocationId()).orElse(null);
            staff.setLocation(loc);
        }

        return toDTO(staffRepository.save(staff));
    }

    @Transactional
    public void deleteStaff(UUID staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", staffId));
        staff.getUser().setIsActive(false);
        userRepository.save(staff.getUser());
        staff.setIsAvailable(false);
        staffRepository.save(staff);
    }

    private StaffDTO toDTO(Staff s) {
        User u = s.getUser();
        return StaffDTO.builder()
                .id(s.getId())
                .userId(u.getId())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .phone(u.getPhone())
                .roleId(u.getRole().getId())
                .roleName(u.getRole().getDisplayName())
                .locationId(s.getLocation() != null ? s.getLocation().getId() : null)
                .locationName(s.getLocation() != null ? s.getLocation().getName() : null)
                .specialization(s.getSpecialization())
                .bio(s.getBio())
                .workingHours(s.getWorkingHours())
                .isAvailable(s.getIsAvailable())
                .isActive(u.getIsActive())
                .build();
    }
}

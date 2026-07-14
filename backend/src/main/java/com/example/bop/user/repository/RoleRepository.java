package com.example.bop.user.repository;

import com.example.bop.user.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Page<Role> findByTenantId(UUID tenantId, Pageable pageable);
    Optional<Role> findByTenantIdAndName(UUID tenantId, String name);
}

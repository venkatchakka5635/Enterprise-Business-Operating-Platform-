package com.example.bop.user.repository;

import com.example.bop.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByTenantIdAndEmail(UUID tenantId, String email);
    Optional<User> findByEmail(String email);
    Page<User> findByTenantId(UUID tenantId, Pageable pageable);
    boolean existsByTenantIdAndEmail(UUID tenantId, String email);
}

package com.example.bop.crm.repository;

import com.example.bop.crm.entity.CustomerInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CustomerInteractionRepository extends JpaRepository<CustomerInteraction, UUID> {
    List<CustomerInteraction> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
}

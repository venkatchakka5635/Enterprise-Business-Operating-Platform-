package com.example.bop.config;

import com.example.bop.tenant.dto.TenantRegistrationRequest;
import com.example.bop.tenant.repository.TenantRepository;
import com.example.bop.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {
            if (tenantRepository.count() == 0) {
                log.info("No tenants found. Seeding initial demo tenant...");
                TenantRegistrationRequest req = new TenantRegistrationRequest();
                req.setBusinessName("Demo Spa & Salon");
                req.setIndustryType("Wellness");
                req.setOwnerFirstName("Admin");
                req.setOwnerLastName("User");
                req.setOwnerEmail("admin@example.com");
                req.setOwnerPassword("admin123");
                req.setLocationName("Downtown Clinic");
                req.setLocationCity("San Francisco");
                tenantService.registerTenant(req);
                log.info("Demo tenant seeded successfully. Login: admin@example.com / admin123");
            }
        };
    }
}

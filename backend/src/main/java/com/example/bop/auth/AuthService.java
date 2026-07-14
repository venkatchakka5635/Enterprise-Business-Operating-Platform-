package com.example.bop.auth;

import com.example.bop.common.exception.ResourceNotFoundException;
import com.example.bop.tenant.entity.Tenant;
import com.example.bop.tenant.repository.TenantRepository;
import com.example.bop.user.entity.User;
import com.example.bop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!user.getIsActive()) {
            throw new BadCredentialsException("Account is deactivated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        Tenant tenant = tenantRepository.findById(user.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", user.getTenantId()));

        String accessToken = jwtService.generateAccessToken(
                user.getId(), user.getTenantId(), user.getEmail(), user.getRole().getName());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().getName())
                .tenantId(user.getTenantId().toString())
                .userId(user.getId().toString())
                .tenantName(tenant.getName())
                .build();
    }
}

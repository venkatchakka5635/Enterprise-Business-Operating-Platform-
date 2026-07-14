package com.example.bop.auth;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String tenantId;
    private String userId;
    private String tenantName;
}

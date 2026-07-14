package com.example.bop.config;

import com.example.bop.common.tenant.TenantContext;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final EntityManager entityManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantInterceptor(entityManager))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/**", "/api/portal/auth/**");
    }

    @RequiredArgsConstructor
    static class TenantInterceptor implements HandlerInterceptor {

        private final EntityManager entityManager;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            UUID tenantId = TenantContext.getTenantId();
            if (tenantId != null) {
                Session session = entityManager.unwrap(Session.class);
                session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
            }
            return true;
        }
    }
}

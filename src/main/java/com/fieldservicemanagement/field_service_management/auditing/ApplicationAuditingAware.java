package com.fieldservicemanagement.field_service_management.auditing;

import com.fieldservicemanagement.field_service_management.entity.Users;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditingAware implements AuditorAware<Long> {

    @Override
    public @NonNull Optional<Long> getCurrentAuditor() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        Users userPrincipal = (Users) authentication.getPrincipal();
        if(userPrincipal == null)
            return Optional.empty();
        return Optional.of(userPrincipal.getId());
    }
}
package com.fieldservicemanagement.field_service_management.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.TYPE_PARAMETER})
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}

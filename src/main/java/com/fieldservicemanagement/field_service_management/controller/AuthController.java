package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.request.LoginRequest;
import com.fieldservicemanagement.field_service_management.common.request.RefreshTokenRequest;
import com.fieldservicemanagement.field_service_management.common.response.ApiResponse;
import com.fieldservicemanagement.field_service_management.common.response.LoginResponse;
import com.fieldservicemanagement.field_service_management.common.response.UserAccessDTO;
import com.fieldservicemanagement.field_service_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BaseURL.API + BaseURL.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(BaseURL.LOGIN)
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(200).body(authService.login(loginRequest));
    }

    @PostMapping(BaseURL.REFRESH_TOKEN)
    public ResponseEntity<ApiResponse<UserAccessDTO>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.status(200).body(authService.refreshToken(refreshTokenRequest));
    }
}

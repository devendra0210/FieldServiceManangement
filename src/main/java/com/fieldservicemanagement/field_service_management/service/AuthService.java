package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.request.LoginRequest;
import com.fieldservicemanagement.field_service_management.common.request.RefreshTokenRequest;
import com.fieldservicemanagement.field_service_management.common.response.LoginResponse;
import com.fieldservicemanagement.field_service_management.common.response.UserAccessDTO;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

    UserAccessDTO refreshToken(RefreshTokenRequest refreshTokenRequest);
}

package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.request.LoginRequest;
import com.fieldservicemanagement.field_service_management.common.request.RefreshTokenRequest;
import com.fieldservicemanagement.field_service_management.common.response.ApiResponse;
import com.fieldservicemanagement.field_service_management.common.response.LoginResponse;
import com.fieldservicemanagement.field_service_management.common.response.UserAccessDTO;

public interface AuthService {

    ApiResponse<LoginResponse> login(LoginRequest loginRequest);

    ApiResponse<UserAccessDTO> refreshToken(RefreshTokenRequest refreshTokenRequest);
}

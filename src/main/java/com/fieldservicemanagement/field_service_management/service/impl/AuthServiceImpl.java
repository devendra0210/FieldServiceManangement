package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.request.LoginRequest;
import com.fieldservicemanagement.field_service_management.common.request.RefreshTokenRequest;
import com.fieldservicemanagement.field_service_management.common.response.ApiResponse;
import com.fieldservicemanagement.field_service_management.common.response.LoginResponse;
import com.fieldservicemanagement.field_service_management.common.response.UserAccessDTO;
import com.fieldservicemanagement.field_service_management.config.prop.JwtProp;
import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.exception.InvalidCredentialsException;
import com.fieldservicemanagement.field_service_management.exception.InvalidTokenTypeException;
import com.fieldservicemanagement.field_service_management.exception.UserNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.security.JwtProvider;
import com.fieldservicemanagement.field_service_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProp jwtProp;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        Users user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        UserAccessDTO userAccessDTO = generatedToken(loginRequest.getEmail());

        LoginResponse loginResponse = new LoginResponse(
                userAccessDTO,
                user.getRole()
        );

        return new ApiResponse<>(true, "successfully", loginResponse);
    }

    @Override
    public ApiResponse<UserAccessDTO> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (!jwtProvider.isRefreshToken(refreshTokenRequest.getRefreshToken())) {
            throw new InvalidTokenTypeException("Refresh token required");
        }

        String email = jwtProvider.getEmailFromToken(refreshTokenRequest.getRefreshToken());

        UserAccessDTO userAccessDTO = generatedToken(email);
        return new ApiResponse<>(true, "successfully", userAccessDTO);
    }

    private UserAccessDTO generatedToken(String email) {
        String accessToken = jwtProvider.generateToken(email, true);
        String refreshToken = jwtProvider.generateToken(email, false);

        return new UserAccessDTO(
                "Bearer",
                accessToken,
                jwtProp.getAccessTtl().toMillis(),
                refreshToken,
                jwtProp.getRefreshTtl().toMillis()
        );
    }
}

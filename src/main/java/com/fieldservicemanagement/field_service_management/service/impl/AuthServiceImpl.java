package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.request.LoginRequest;
import com.fieldservicemanagement.field_service_management.common.request.RefreshTokenRequest;
import com.fieldservicemanagement.field_service_management.common.response.LoginResponse;
import com.fieldservicemanagement.field_service_management.common.response.UserAccessDTO;
import com.fieldservicemanagement.field_service_management.config.prop.JwtProp;
import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.exception.InvalidCredentialsException;
import com.fieldservicemanagement.field_service_management.exception.InvalidTokenTypeException;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
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
    public LoginResponse login(LoginRequest loginRequest) {

        Users user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomNotFoundException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        UserAccessDTO userAccessDTO = generatedToken(loginRequest.getEmail());

        return new LoginResponse(
                userAccessDTO,
                user.getRole()
        );
    }

    @Override
    public UserAccessDTO refreshToken(RefreshTokenRequest refreshTokenRequest) {

        if (!jwtProvider.isRefreshToken(refreshTokenRequest.getRefreshToken())) {
            throw new InvalidTokenTypeException("Refresh token required");
        }

        if (jwtProvider.isTokenExpired(refreshTokenRequest.getRefreshToken())) {
            throw new InvalidTokenTypeException("Refresh token expired");
        }

        String email = jwtProvider.getEmailFromToken(refreshTokenRequest.getRefreshToken());

        return generatedToken(email);
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

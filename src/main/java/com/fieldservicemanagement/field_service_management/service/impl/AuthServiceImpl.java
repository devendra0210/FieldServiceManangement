package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.request.LoginRequest;
import com.fieldservicemanagement.field_service_management.common.response.ApiResponse;
import com.fieldservicemanagement.field_service_management.common.response.LoginResponse;
import com.fieldservicemanagement.field_service_management.common.response.UserAccessDTO;
import com.fieldservicemanagement.field_service_management.config.prop.JwtProp;
import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.exception.InvalidCredentialsException;
import com.fieldservicemanagement.field_service_management.exception.UserNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.UserRepository;
import com.fieldservicemanagement.field_service_management.security.JwtProvider;
import com.fieldservicemanagement.field_service_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
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

        String accessToken = jwtProvider.generateToken(loginRequest.getEmail(), true);
        String refreshToken = jwtProvider.generateToken(loginRequest.getEmail(), false);

        UserAccessDTO userAccessDTO = new UserAccessDTO(
                "Bearer",
                accessToken,
                jwtProp.getAccessTtl().toMillis(),
                refreshToken,
                jwtProp.getRefreshTtl().toMillis()
        );

        LoginResponse loginResponse = new LoginResponse(
                userAccessDTO,
                user.getRole()
        );


        return new ApiResponse<>(true, "successfully", loginResponse);
    }
}

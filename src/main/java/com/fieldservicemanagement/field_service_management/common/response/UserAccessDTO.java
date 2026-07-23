package com.fieldservicemanagement.field_service_management.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDTO {

    private String tokenType;

    private String accessToken;

    private Long accessTokenExpire;

    private String refreshToken;

    private Long refreshTokenExpire;
}

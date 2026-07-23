package com.fieldservicemanagement.field_service_management.common.response;

import com.fieldservicemanagement.field_service_management.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private UserAccessDTO access;

    private RoleName role;
}

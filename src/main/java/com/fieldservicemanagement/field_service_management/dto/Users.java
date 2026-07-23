package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    private Long id;

    private String name;

    private String email;

    private String role;
}

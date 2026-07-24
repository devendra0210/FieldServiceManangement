package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTO {

    private Long id;

    private String name;

    private String email;

    private String role;
}

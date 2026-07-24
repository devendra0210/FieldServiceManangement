package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteDTO {

    private Long id;

    private Long customerId;

    private String name;

    private String address;

}
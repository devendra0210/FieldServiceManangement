package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    private Long id;

    private String code;

    private String title;

    private String priority;

    private String status;

    private LocalDateTime slaDueAt;

    private Long customerId;

    private Long siteId;

    private Long assignedTo;

}
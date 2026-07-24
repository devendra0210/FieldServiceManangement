package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLogDTO {

    private Long id;

    private Long workOrderId;

    private Long technicianId;

    private Integer minutes;

    private String note;

}

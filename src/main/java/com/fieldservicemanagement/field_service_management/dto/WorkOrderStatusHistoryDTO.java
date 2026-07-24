package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderStatusHistoryDTO {

    private Long id;

    private Long workOrderId;

    private String fromStatus;

    private String toStatus;

    private String changedBy;

    private LocalDateTime changedAt;

}

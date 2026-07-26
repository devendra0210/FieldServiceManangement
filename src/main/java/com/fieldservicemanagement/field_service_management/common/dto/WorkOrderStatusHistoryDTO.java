package com.fieldservicemanagement.field_service_management.common.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderStatusHistoryDTO {

    private Long id;

    @NotNull(message = "Work Order Id is required")
    @Min(value = 1, message = "Work Order Id must be greater than 0")
    private Long workOrderId;

    @NotBlank(message = "From Status is required")
    @Pattern(
            regexp = "ASSIGNED|IN_PROGRESS|COMPLETED|CANCELLED|CLOSED|OH_HOLD|NEW",
            message = "From Status must be one of: OPEN, IN_PROGRESS, COMPLETED, CANCELLED"
    )
    private String fromStatus;

    @NotBlank(message = "To Status is required")
    @Pattern(
            regexp = "ASSIGNED|IN_PROGRESS|COMPLETED|CANCELLED|CLOSED|OH_HOLD|NEW",
            message = "To Status must be one of: OPEN, IN_PROGRESS, COMPLETED, CANCELLED"
    )
    private String toStatus;

    @NotBlank(message = "Changed By is required")
    private String changedBy;

    @NotNull(message = "Changed At is required")
    @FutureOrPresent(message = "Changed At must be the current or a future date and time")
    private LocalDateTime changedAt;

}

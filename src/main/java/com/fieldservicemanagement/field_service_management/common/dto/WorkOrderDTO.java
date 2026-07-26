package com.fieldservicemanagement.field_service_management.common.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderDTO {

    private Long id;

    @NotBlank(message = "Work order code is required")
    @Size(min = 2, max = 50, message = "Work order code must be between 2 and 50 characters")
    private String code;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 150, message = "Title must be between 5 and 150 characters")
    private String title;

    @NotBlank(message = "Priority is required")
    @Pattern(
            regexp = "LOW|MEDIUM|HIGH|CRITICAL",
            message = "Priority must be one of: LOW, MEDIUM, HIGH, CRITICAL"
    )
    private String priority;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "ASSIGNED|IN_PROGRESS|COMPLETED|CANCELLED|CLOSED|OH_HOLD|NEW",
            message = "Status must be one of: OPEN, IN_PROGRESS, COMPLETED, CANCELLED"
    )
    private String status;

    @NotNull(message = "SLA Due Date is required")
    @Future(message = "SLA Due Date must be a future date and time")
    private LocalDateTime slaDueAt;

    @NotNull(message = "Customer Id is required")
    @Min(value = 1, message = "Customer Id must be greater than 0")
    private Long customerId;

    @NotNull(message = "Site Id is required")
    @Min(value = 1, message = "Site Id must be greater than 0")
    private Long siteId;

    @NotNull(message = "Assigned User Id is required")
    @Min(value = 1, message = "Assigned User Id must be greater than 0")
    private Long assignedTo;
}
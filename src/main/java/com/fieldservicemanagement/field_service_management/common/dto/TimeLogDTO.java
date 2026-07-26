package com.fieldservicemanagement.field_service_management.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLogDTO {

    private Long id;

    @NotNull(message = "Work Order Id is required")
    @Min(value = 1, message = "Work Order Id must be greater than 0")
    private Long workOrderId;

    @NotNull(message = "Technician Id is required")
    @Min(value = 1, message = "Technician Id must be greater than 0")
    private Long technicianId;

    @NotNull(message = "Minutes are required")
    @Min(value = 1, message = "Minutes must be greater than 0")
    private Integer minutes;

    @NotBlank(message = "Note is required")
    @Size(min = 5, max = 500, message = "Note must be between 5 and 500 characters")
    private String note;

}

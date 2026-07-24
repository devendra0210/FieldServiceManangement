package com.fieldservicemanagement.field_service_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartUsageDTO {

    private Long id;

    @NotNull(message = "Work Order Id is required")
    @Min(value = 1, message = "Work Order Id must be greater than 0")
    private Long workOrderId;

    @NotNull(message = "Part Id is required")
    @Min(value = 1, message = "Part Id must be greater than 0")
    private Long partId;

    @NotNull(message = "Quantity used is required")
    @Min(value = 1, message = "Quantity used must be at least 1")
    private Integer qtyUsed;

}

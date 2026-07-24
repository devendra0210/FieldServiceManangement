package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartUsageDTO {

    private Long id;

    private Long workOrderId;

    private Long partId;

    private Integer qtyUsed;

}

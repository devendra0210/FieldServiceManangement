package com.fieldservicemanagement.field_service_management.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Part {

    private Long id;

    private String name;

    private String sku;

    private BigDecimal unitCost;

    private Integer stockQty;

}

package com.fieldservicemanagement.field_service_management.entity;

import com.fieldservicemanagement.field_service_management.base.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "part")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Part extends AbsEntity {

    private String name;

    private String sku;

    @Column(name = "unit_cost")
    private BigDecimal unitCost;

    @Column(name = "stock_qty")
    private Integer stockQty;

    @OneToMany(mappedBy = "part")
    private List<PartUsage> partUsages;
}
package com.fieldservicemanagement.field_service_management.entity;

import com.fieldservicemanagement.field_service_management.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "part_usage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartUsage extends BaseEntity {

    @Column(name = "qty_used")
    private Integer qtyUsed;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;
}
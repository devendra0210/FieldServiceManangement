package com.fieldservicemanagement.field_service_management.entity;

import com.fieldservicemanagement.field_service_management.base.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "time_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLog extends AbsEntity {

    private Integer minutes;

    private String note;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Users technician;
}
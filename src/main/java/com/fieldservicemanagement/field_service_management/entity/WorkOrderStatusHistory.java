package com.fieldservicemanagement.field_service_management.entity;

import com.fieldservicemanagement.field_service_management.base.AbsEntity;
import com.fieldservicemanagement.field_service_management.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_order_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderStatusHistory extends AbsEntity {

    @Column(name = "from_status")
    @Enumerated(value = EnumType.STRING)
    private WorkStatus fromStatus;

    @Column(name = "to_status")
    @Enumerated(value = EnumType.STRING)
    private WorkStatus toStatus;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private Users changedBy;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;
}
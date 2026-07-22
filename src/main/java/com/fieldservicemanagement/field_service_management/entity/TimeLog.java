package com.fieldservicemanagement.field_service_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "time_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minutes;

    private String note;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Users technician;
}
package com.fieldservicemanagement.field_service_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "work_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String title;

    private String priority;

    private String status;

    @Column(name = "sla_due_at")
    private LocalDateTime slaDueAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Users assignedTo;

    @OneToMany(mappedBy = "workOrder")
    private List<TimeLog> timeLogs;

    @OneToMany(mappedBy = "workOrder")
    private List<PartUsage> partUsages;

    @OneToMany(mappedBy = "workOrder")
    private List<WorkOrderStatusHistory> statusHistory;
}
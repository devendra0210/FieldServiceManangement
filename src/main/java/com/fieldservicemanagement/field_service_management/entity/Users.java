package com.fieldservicemanagement.field_service_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String role;

    @Column(name = "password_hash")
    private String passwordHash;

    @OneToMany(mappedBy = "assignedTo")
    private List<WorkOrder> assignedWorkOrders;

    @OneToMany(mappedBy = "technician")
    private List<TimeLog> timeLogs;

    @OneToMany(mappedBy = "changedBy")
    private List<WorkOrderStatusHistory> statusHistory;
}
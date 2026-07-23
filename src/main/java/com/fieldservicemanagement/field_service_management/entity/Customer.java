package com.fieldservicemanagement.field_service_management.entity;

import com.fieldservicemanagement.field_service_management.base.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @OneToMany(mappedBy = "customer")
    private List<Site> sites;

    @OneToMany(mappedBy = "customer")
    private List<WorkOrder> workOrders;
}
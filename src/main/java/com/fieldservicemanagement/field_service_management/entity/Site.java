package com.fieldservicemanagement.field_service_management.entity;
import com.fieldservicemanagement.field_service_management.base.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "site")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Site extends AbsEntity {

    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "site")
    private List<WorkOrder> workOrders;
}
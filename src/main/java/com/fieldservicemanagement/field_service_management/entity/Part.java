package com.fieldservicemanagement.field_service_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "part")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String sku;

    @Column(name = "unit_cost")
    private Double unitCost;

    @Column(name = "stock_qty")
    private Integer stockQty;

    @OneToMany(mappedBy = "part")
    private List<PartUsage> partUsages = new ArrayList<>();
}
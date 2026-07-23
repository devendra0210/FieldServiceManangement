package com.fieldservicemanagement.field_service_management.repository;

import com.fieldservicemanagement.field_service_management.entity.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    Page<WorkOrder> findByStatus(String status, Pageable pageable);

    Page<WorkOrder> findByPriority(String priority, Pageable pageable);

    Page<WorkOrder> findByCustomerId(Long customerId, Pageable pageable);

    Page<WorkOrder> findBySiteId(Long siteId, Pageable pageable);

    Page<WorkOrder> findByAssignedToId(Long assignedToId, Pageable pageable);

    Page<WorkOrder> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    boolean existsByCode(String code);
}

package com.fieldservicemanagement.field_service_management.repository;

import com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderStatusHistoryRepository
        extends JpaRepository<WorkOrderStatusHistory, Long> {

    List<WorkOrderStatusHistory> findByWorkOrderIdOrderByChangedAtAsc(Long workOrderId);
}
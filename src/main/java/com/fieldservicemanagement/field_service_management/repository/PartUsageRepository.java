package com.fieldservicemanagement.field_service_management.repository;

import com.fieldservicemanagement.field_service_management.entity.PartUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartUsageRepository extends JpaRepository<PartUsage, Long> {

    List<PartUsage> findByWorkOrderId(Long workOrderId);

    List<PartUsage> findByPartId(Long partId);
}

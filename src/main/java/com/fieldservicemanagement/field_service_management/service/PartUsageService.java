package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.PartUsageDTO;

import java.util.List;
import org.springframework.data.domain.Page;

public interface PartUsageService {

    PartUsageDTO createPartUsage(PartUsageDTO partUsage);

    PartUsageDTO getPartUsageById(Long id);

    List<PartUsageDTO> getPartUsageByWorkOrder(Long workOrderId);

    Page<PartUsageDTO> getAllPartUsages(
            int page,
            int size,
            String sortBy,
            String sortDir);

    PartUsageDTO updatePartUsage(Long id, PartUsageDTO partUsageDTO);

    void deletePartUsage(Long id);

}
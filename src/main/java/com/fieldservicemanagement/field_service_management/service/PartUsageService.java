package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.PartUsageDTO;

import java.util.List;

public interface PartUsageService {

    PartUsageDTO createPartUsage(PartUsageDTO partUsage);

    PartUsageDTO getPartUsageById(Long id);

    List<PartUsageDTO> getPartUsageByWorkOrder(Long workOrderId);

    List<PartUsageDTO> getAllPartUsages();

    PartUsageDTO updatePartUsage(Long id, PartUsageDTO partUsageDTO);

    void deletePartUsage(Long id);

}
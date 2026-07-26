package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.PartUsageDTO;

import java.util.List;

import com.fieldservicemanagement.field_service_management.common.response.PageResponse;

public interface PartUsageService {

    PartUsageDTO createPartUsage(PartUsageDTO partUsage);

    PartUsageDTO getPartUsageById(Long id);

    List<PartUsageDTO> getPartUsageByWorkOrder(Long workOrderId);

    PageResponse<PartUsageDTO> getPage(int page, int size, Long workOrderId, Long partId);


    PartUsageDTO updatePartUsage(Long id, PartUsageDTO partUsageDTO);

    void deletePartUsage(Long id);

}
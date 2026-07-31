package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderStatusHistoryDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;

public interface WorkOrderStatusHistoryService {

    WorkOrderStatusHistoryDTO createHistory(WorkOrderStatusHistoryDTO history);

    WorkOrderStatusHistoryDTO getHistoryById(Long id);

    PageResponse<WorkOrderStatusHistoryDTO> getPage(int page, int size, Long workOrderId, SortDirection sortDirection);

    WorkOrderStatusHistoryDTO updateHistory(Long id, WorkOrderStatusHistoryDTO historyDTO);

    void deleteHistory(Long id);

}
package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderStatusHistoryDTO;

import java.util.List;

public interface WorkOrderStatusHistoryService {

    WorkOrderStatusHistoryDTO createHistory(WorkOrderStatusHistoryDTO history);

    WorkOrderStatusHistoryDTO getHistoryById(Long id);

    List<WorkOrderStatusHistoryDTO> getAllHistory();

    WorkOrderStatusHistoryDTO updateHistory(Long id, WorkOrderStatusHistoryDTO historyDTO);

    void deleteHistory(Long id);

}
package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderStatusHistoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WorkOrderStatusHistoryService {

    WorkOrderStatusHistoryDTO createHistory(WorkOrderStatusHistoryDTO history);

    WorkOrderStatusHistoryDTO getHistoryById(Long id);

    Page<WorkOrderStatusHistoryDTO> getAllHistory(
            int page,
            int size,
            String sortBy,
            String sortDir);

    WorkOrderStatusHistoryDTO updateHistory(Long id, WorkOrderStatusHistoryDTO historyDTO);

    void deleteHistory(Long id);

}
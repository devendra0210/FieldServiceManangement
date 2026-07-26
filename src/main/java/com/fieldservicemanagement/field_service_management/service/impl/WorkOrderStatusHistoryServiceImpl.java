package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderStatusHistoryDTO;
import com.fieldservicemanagement.field_service_management.enums.WorkStatus;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderStatusHistoryRepository;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.service.WorkOrderStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkOrderStatusHistoryServiceImpl implements WorkOrderStatusHistoryService {

    private final WorkOrderStatusHistoryRepository historyRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UsersRepository userRepository;

    // Create Status History
    public WorkOrderStatusHistoryDTO createHistory(WorkOrderStatusHistoryDTO history) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository
                .findById(history.getWorkOrderId())
                .orElseThrow(() -> new CustomNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Users user = userRepository
                .findById(Long.valueOf(history.getChangedBy()))
                .orElseThrow(() -> new CustomNotFoundException("User not found"));

        com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity =
                new com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory();

        entity.setWorkOrder(workOrder);
        entity.setFromStatus(WorkStatus.valueOf(history.getFromStatus()));
        entity.setToStatus(WorkStatus.valueOf(history.getToStatus()));
        entity.setChangedBy(user);
        entity.setChangedAt(history.getChangedAt());

        entity = historyRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get History By Id
    public WorkOrderStatusHistoryDTO getHistoryById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity =
                historyRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomNotFoundException("History not found"));

        return mapToDTO(entity);
    }

    // Get All History
    public List<WorkOrderStatusHistoryDTO> getAllHistory() {

        return historyRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    // Delete History
    public void deleteHistory(Long id) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity =
                historyRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomNotFoundException("History not found"));

        historyRepository.delete(entity);
    }

    // Update Status History
    public WorkOrderStatusHistoryDTO updateHistory(
            Long id,
            WorkOrderStatusHistoryDTO historyDTO) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity =
                historyRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomNotFoundException("History not found"));

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder =
                workOrderRepository.findById(historyDTO.getWorkOrderId())
                        .orElseThrow(() ->
                                new CustomNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Users user =
                userRepository.findById(Long.valueOf(historyDTO.getChangedBy()))
                        .orElseThrow(() ->
                                new CustomNotFoundException("User not found"));

        entity.setWorkOrder(workOrder);
        entity.setFromStatus(WorkStatus.valueOf(historyDTO.getFromStatus()));
        entity.setToStatus(WorkStatus.valueOf(historyDTO.getToStatus()));
        entity.setChangedBy(user);
        entity.setChangedAt(historyDTO.getChangedAt());

        entity = historyRepository.save(entity);

        return mapToDTO(entity);
    }

    // Entity -> DTO
    private WorkOrderStatusHistoryDTO mapToDTO(
            com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity) {

        WorkOrderStatusHistoryDTO dto = new WorkOrderStatusHistoryDTO();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setFromStatus(String.valueOf(entity.getFromStatus()));
        dto.setToStatus(String.valueOf(entity.getToStatus()));

        if (entity.getChangedBy() != null) {
            dto.setChangedBy(String.valueOf(entity.getChangedBy().getId()));
        }

        dto.setChangedAt(entity.getChangedAt());

        return dto;
    }
}

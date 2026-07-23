package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.WorkOrderStatusHistory;
import com.fieldservicemanagement.field_service_management.enums.WorkStatus;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderStatusHistoryRepository;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderStatusHistoryService {

    @Autowired
    private WorkOrderStatusHistoryRepository historyRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private UsersRepository userRepository;

    // Create Status History
    public WorkOrderStatusHistory createHistory(WorkOrderStatusHistory history) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository
                .findById(history.getWorkOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Users user = userRepository
                .findById(Long.valueOf(history.getChangedBy()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

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
    public WorkOrderStatusHistory getHistoryById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity =
                historyRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("History not found"));

        return mapToDTO(entity);
    }

    // Get History By Work Order
    public List<WorkOrderStatusHistory> getHistory(Long workOrderId) {

        return historyRepository
                .findByWorkOrderIdOrderByChangedAtAsc(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Delete History
    public void deleteHistory(Long id) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity =
                historyRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("History not found"));

        historyRepository.delete(entity);
    }

    // Entity -> DTO
    private WorkOrderStatusHistory mapToDTO(
            com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory entity) {

        WorkOrderStatusHistory dto = new WorkOrderStatusHistory();

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

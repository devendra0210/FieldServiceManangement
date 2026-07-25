package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.dto.TimeLogDTO;
import com.fieldservicemanagement.field_service_management.repository.TimeLogRepository;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import com.fieldservicemanagement.field_service_management.service.TimeLogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeLogServiceImpl implements TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UsersRepository userRepository;

    // Create Time Log
    public TimeLogDTO createTimeLog(TimeLogDTO timeLog) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository
                .findById(timeLog.getWorkOrderId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Users technician = userRepository
                .findById(timeLog.getTechnicianId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Technician not found"));

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity =
                new com.fieldservicemanagement.field_service_management.entity.TimeLog();

        entity.setWorkOrder(workOrder);
        entity.setTechnician(technician);
        entity.setMinutes(timeLog.getMinutes());
        entity.setNote(timeLog.getNote());

        entity = timeLogRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Time Log By Id
    public TimeLogDTO getTimeLogById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity = timeLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Time Log not found"));

        return mapToDTO(entity);
    }

    // Get All Time Logs
    public List<TimeLogDTO> getAllTimeLogs() {

        return timeLogRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Time Logs By Work Order
    public List<TimeLogDTO> getTimeLogsByWorkOrder(Long workOrderId) {

        return timeLogRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Time Logs By Technician
    public List<TimeLogDTO> getTimeLogsByTechnician(Long technicianId) {

        return timeLogRepository.findByTechnicianId(technicianId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Time Log
    public TimeLogDTO updateTimeLog(Long id, TimeLogDTO timeLog) {

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity = timeLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Time Log not found"));

        entity.setMinutes(timeLog.getMinutes());
        entity.setNote(timeLog.getNote());

        entity = timeLogRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Time Log
    public void deleteTimeLog(Long id) {

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity = timeLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Time Log not found"));

        timeLogRepository.delete(entity);
    }

    // Entity -> DTO
    private TimeLogDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.TimeLog entity) {

        TimeLogDTO dto = new TimeLogDTO();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setTechnicianId(entity.getTechnician().getId());
        dto.setMinutes(entity.getMinutes());
        dto.setNote(entity.getNote());

        return dto;
    }
}
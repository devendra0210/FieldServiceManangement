package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.TimeLog;
import com.fieldservicemanagement.field_service_management.repository.TimeLogRepository;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeLogService {

    @Autowired
    private TimeLogRepository timeLogRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private UsersRepository userRepository;

    // Create Time Log
    public TimeLog createTimeLog(TimeLog timeLog) {

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
    public TimeLog getTimeLogById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity = timeLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Time Log not found"));

        return mapToDTO(entity);
    }

    // Get All Time Logs
    public List<TimeLog> getAllTimeLogs() {

        return timeLogRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Time Logs By Work Order
    public List<TimeLog> getTimeLogsByWorkOrder(Long workOrderId) {

        return timeLogRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Time Logs By Technician
    public List<TimeLog> getTimeLogsByTechnician(Long technicianId) {

        return timeLogRepository.findByTechnicianId(technicianId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Time Log
    public TimeLog updateTimeLog(Long id, TimeLog timeLog) {

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
    private TimeLog mapToDTO(com.fieldservicemanagement.field_service_management.entity.TimeLog entity) {

        TimeLog dto = new TimeLog();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setTechnicianId(entity.getTechnician().getId());
        dto.setMinutes(entity.getMinutes());
        dto.setNote(entity.getNote());

        return dto;
    }
}
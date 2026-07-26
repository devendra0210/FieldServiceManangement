package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.TimeLogDTO;

import java.util.List;

public interface TimeLogService {

    TimeLogDTO createTimeLog(TimeLogDTO timeLog);

    TimeLogDTO getTimeLogById(Long id);

    List<TimeLogDTO> getAllTimeLogs();

    List<TimeLogDTO> getTimeLogsByWorkOrder(Long workOrderId);

    List<TimeLogDTO> getTimeLogsByTechnician(Long technicianId);

    TimeLogDTO updateTimeLog(Long id, TimeLogDTO timeLog);

    void deleteTimeLog(Long id);
}
package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.TimeLogDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TimeLogService {

    TimeLogDTO createTimeLog(TimeLogDTO timeLog);

    TimeLogDTO getTimeLogById(Long id);

    Page<TimeLogDTO> getAllTimeLogs(
            int page,
            int size,
            String sortBy,
            String sortDir);

    List<TimeLogDTO> getTimeLogsByWorkOrder(Long workOrderId);

    List<TimeLogDTO> getTimeLogsByTechnician(Long technicianId);

    TimeLogDTO updateTimeLog(Long id, TimeLogDTO timeLog);

    void deleteTimeLog(Long id);
}
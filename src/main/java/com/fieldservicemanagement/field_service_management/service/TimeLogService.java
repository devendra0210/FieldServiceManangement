package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.TimeLogDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;

import java.util.List;

public interface TimeLogService {

    TimeLogDTO createTimeLog(TimeLogDTO timeLog);

    TimeLogDTO getTimeLogById(Long id);

    PageResponse<TimeLogDTO> getPage(int page, int size, Long workOrderId, Long technicianId, SortDirection sortDirection);


    List<TimeLogDTO> getTimeLogsByWorkOrder(Long workOrderId);

    List<TimeLogDTO> getTimeLogsByTechnician(Long technicianId);

    TimeLogDTO updateTimeLog(Long id, TimeLogDTO timeLog);

    void deleteTimeLog(Long id);
}
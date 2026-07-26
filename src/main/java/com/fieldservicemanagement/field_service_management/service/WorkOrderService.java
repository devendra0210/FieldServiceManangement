package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkOrderService {

    WorkOrderDTO createWorkOrder(WorkOrderDTO workOrder);

    WorkOrderDTO getWorkOrderById(Long id);

    PageResponse<WorkOrderDTO> getPage(int page, int size,String code, String title);

    Page<WorkOrderDTO> getByStatus(String status, Pageable pageable);

    Page<WorkOrderDTO> getByPriority(String priority, Pageable pageable);

    WorkOrderDTO assignTechnician(Long workOrderId, Long technicianId);

    WorkOrderDTO changeStatus(Long workOrderId, String status);

    WorkOrderDTO updateWorkOrder(Long id, WorkOrderDTO workOrder);

    void deleteWorkOrder(Long id);

}
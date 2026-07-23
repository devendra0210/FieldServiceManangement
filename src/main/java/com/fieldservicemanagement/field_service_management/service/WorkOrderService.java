package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.WorkOrder;
import com.fieldservicemanagement.field_service_management.repository.CustomerRepository;
import com.fieldservicemanagement.field_service_management.repository.SiteRepository;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UsersRepository userRepository;

    // Create Work Order
    public WorkOrder createWorkOrder(WorkOrder workOrder) {

        com.fieldservicemanagement.field_service_management.entity.Customer customer = customerRepository.findById(workOrder.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        com.fieldservicemanagement.field_service_management.entity.Site site = siteRepository.findById(workOrder.getSiteId())
                .orElseThrow(() -> new EntityNotFoundException("Site not found"));

        com.fieldservicemanagement.field_service_management.entity.Users technician = null;

        if (workOrder.getAssignedTo() != null) {
            technician = userRepository.findById(workOrder.getAssignedTo())
                    .orElseThrow(() -> new EntityNotFoundException("Technician not found"));
        }

        com.fieldservicemanagement.field_service_management.entity.WorkOrder entity = new com.fieldservicemanagement.field_service_management.entity.WorkOrder();

        entity.setCode(workOrder.getCode());
        entity.setTitle(workOrder.getTitle());
        entity.setPriority(workOrder.getPriority());
        entity.setStatus(workOrder.getStatus());
        entity.setSlaDueAt(workOrder.getSlaDueAt());

        entity.setCustomer(customer);
        entity.setSite(site);
        entity.setAssignedTo(technician);

        entity = workOrderRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get By Id
    public WorkOrder getWorkOrderById(Long id) {

        return mapToDTO(workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkOrder not found")));
    }

    // Get All
    public Page<WorkOrder> getAllWorkOrders(Pageable pageable) {

        return workOrderRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // Get By Status
    public Page<WorkOrder> getByStatus(String status, Pageable pageable) {

        return workOrderRepository.findByStatus(status, pageable)
                .map(this::mapToDTO);
    }

    // Get By Priority
    public Page<WorkOrder> getByPriority(String priority, Pageable pageable) {

        return workOrderRepository.findByPriority(priority, pageable)
                .map(this::mapToDTO);
    }

    // Assign Technician
    public WorkOrder assignTechnician(Long workOrderId, Long technicianId) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new EntityNotFoundException("WorkOrder not found"));

        com.fieldservicemanagement.field_service_management.entity.Users technician = userRepository.findById(technicianId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        workOrder.setAssignedTo(technician);

        return mapToDTO(workOrderRepository.save(workOrder));
    }

    // Change Status
    public WorkOrder changeStatus(Long workOrderId, String status) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new EntityNotFoundException("WorkOrder not found"));

        workOrder.setStatus(status);

        return mapToDTO(workOrderRepository.save(workOrder));
    }

    // Update
    public WorkOrder updateWorkOrder(Long id, WorkOrder workOrder) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder entity = workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkOrder not found"));

        entity.setCode(workOrder.getCode());
        entity.setTitle(workOrder.getTitle());
        entity.setPriority(workOrder.getPriority());
        entity.setStatus(workOrder.getStatus());
        entity.setSlaDueAt(workOrder.getSlaDueAt());

        entity = workOrderRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete
    public void deleteWorkOrder(Long id) {

        workOrderRepository.deleteById(id);
    }

    // Entity -> DTO
    private WorkOrder mapToDTO(com.fieldservicemanagement.field_service_management.entity.WorkOrder entity) {

        WorkOrder dto = new WorkOrder();

        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setTitle(entity.getTitle());
        dto.setPriority(entity.getPriority());
        dto.setStatus(entity.getStatus());
        dto.setSlaDueAt(entity.getSlaDueAt());

        dto.setCustomerId(entity.getCustomer().getId());
        dto.setSiteId(entity.getSite().getId());

        if (entity.getAssignedTo() != null) {
            dto.setAssignedTo(entity.getAssignedTo().getId());
        }

        return dto;
    }
}
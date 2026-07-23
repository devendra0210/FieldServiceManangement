package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.PartUsage;
import com.fieldservicemanagement.field_service_management.repository.PartRepository;
import com.fieldservicemanagement.field_service_management.repository.PartUsageRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartUsageService {

    @Autowired
    private PartUsageRepository partUsageRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    // Add Part Usage
    public PartUsage addPartUsage(PartUsage partUsage) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository
                .findById(partUsage.getWorkOrderId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Part part = partRepository
                .findById(partUsage.getPartId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Part not found"));

        if (part.getStockQty() < partUsage.getQtyUsed()) {
            throw new RuntimeException("Insufficient stock available.");
        }

        // Reduce Stock
        part.setStockQty(part.getStockQty() - partUsage.getQtyUsed());
        partRepository.save(part);

        com.fieldservicemanagement.field_service_management.entity.PartUsage entity =
                new com.fieldservicemanagement.field_service_management.entity.PartUsage();

        entity.setWorkOrder(workOrder);
        entity.setPart(part);
        entity.setQtyUsed(partUsage.getQtyUsed());

        entity = partUsageRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Usage By Id
    public PartUsage getPartUsageById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.PartUsage entity =
                partUsageRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Part Usage not found"));

        return mapToDTO(entity);
    }

    // Get Usage By Work Order
    public List<PartUsage> getPartUsageByWorkOrder(Long workOrderId) {

        return partUsageRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Delete Usage
    public void deletePartUsage(Long id) {

        com.fieldservicemanagement.field_service_management.entity.PartUsage entity =
                partUsageRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Part Usage not found"));

        // Restore Stock
        com.fieldservicemanagement.field_service_management.entity.Part part = entity.getPart();

        part.setStockQty(part.getStockQty() + entity.getQtyUsed());
        partRepository.save(part);

        partUsageRepository.delete(entity);
    }

    // Entity -> DTO
    private PartUsage mapToDTO(com.fieldservicemanagement.field_service_management.entity.PartUsage entity) {

        PartUsage dto = new PartUsage();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setPartId(entity.getPart().getId());
        dto.setQtyUsed(entity.getQtyUsed());

        return dto;
    }
}

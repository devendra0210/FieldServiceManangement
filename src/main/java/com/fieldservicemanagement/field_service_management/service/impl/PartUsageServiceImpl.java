package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.PartUsageDTO;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.PartRepository;
import com.fieldservicemanagement.field_service_management.repository.PartUsageRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import com.fieldservicemanagement.field_service_management.service.PartUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartUsageServiceImpl implements PartUsageService {

    private final PartUsageRepository partUsageRepository;
    private final PartRepository partRepository;
    private final WorkOrderRepository workOrderRepository;

    // Add Part Usage
    public PartUsageDTO createPartUsage(PartUsageDTO partUsage) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository
                .findById(partUsage.getWorkOrderId())
                .orElseThrow(() ->
                        new CustomNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Part part = partRepository
                .findById(partUsage.getPartId())
                .orElseThrow(() ->
                        new CustomNotFoundException("Part not found"));

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
    public PartUsageDTO getPartUsageById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.PartUsage entity =
                partUsageRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomNotFoundException("Part Usage not found"));

        return mapToDTO(entity);
    }

    // Get Usage By Work Order
    public List<PartUsageDTO> getPartUsageByWorkOrder(Long workOrderId) {

        return partUsageRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PartUsageDTO> getAllPartUsages(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return partUsageRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    //Update usage

    public PartUsageDTO updatePartUsage(Long id, PartUsageDTO partUsageDTO) {

        com.fieldservicemanagement.field_service_management.entity.PartUsage entity =
                partUsageRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomNotFoundException("Part Usage not found"));

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder =
                workOrderRepository.findById(partUsageDTO.getWorkOrderId())
                        .orElseThrow(() ->
                                new CustomNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Part part =
                partRepository.findById(partUsageDTO.getPartId())
                        .orElseThrow(() ->
                                new CustomNotFoundException("Part not found"));

        entity.setWorkOrder(workOrder);
        entity.setPart(part);
        entity.setQtyUsed(partUsageDTO.getQtyUsed());

        entity = partUsageRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Usage
    public void deletePartUsage(Long id) {

        com.fieldservicemanagement.field_service_management.entity.PartUsage entity =
                partUsageRepository.findById(id)
                        .orElseThrow(() ->
                                new CustomNotFoundException("Part Usage not found"));

        // Restore Stock
        com.fieldservicemanagement.field_service_management.entity.Part part = entity.getPart();

        part.setStockQty(part.getStockQty() + entity.getQtyUsed());
        partRepository.save(part);

        partUsageRepository.delete(entity);
    }

    // Entity -> DTO
    private PartUsageDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.PartUsage entity) {

        PartUsageDTO dto = new PartUsageDTO();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setPartId(entity.getPart().getId());
        dto.setQtyUsed(entity.getQtyUsed());

        return dto;
    }
}

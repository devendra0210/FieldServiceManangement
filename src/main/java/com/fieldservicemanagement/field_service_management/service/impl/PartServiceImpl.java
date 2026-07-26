package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.PartDTO;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.PartRepository;
import com.fieldservicemanagement.field_service_management.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    // Create Part
    public PartDTO createPart(PartDTO part) {

        com.fieldservicemanagement.field_service_management.entity.Part entity = new com.fieldservicemanagement.field_service_management.entity.Part();

        entity.setName(part.getName());
        entity.setSku(part.getSku());
        entity.setUnitCost(part.getUnitCost());
        entity.setStockQty(part.getStockQty());

        entity = partRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Part By Id
    public PartDTO getPartById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Part entity = partRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Part not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Parts
    public List<PartDTO> getAllParts() {

        return partRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Part
    public PartDTO updatePart(Long id, PartDTO part) {

        com.fieldservicemanagement.field_service_management.entity.Part entity = partRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Part not found with id : " + id));

        entity.setName(part.getName());
        entity.setSku(part.getSku());
        entity.setUnitCost(part.getUnitCost());
        entity.setStockQty(part.getStockQty());

        entity = partRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Part
    public void deletePart(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Part entity = partRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Part not found with id : " + id));

        partRepository.delete(entity);
    }

    // Entity -> DTO
    private PartDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.Part entity) {

        PartDTO dto = new PartDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSku(entity.getSku());
        dto.setUnitCost(entity.getUnitCost());
        dto.setStockQty(entity.getStockQty());

        return dto;
    }
}

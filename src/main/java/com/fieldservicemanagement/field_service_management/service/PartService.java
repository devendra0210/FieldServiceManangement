package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.Part;
import com.fieldservicemanagement.field_service_management.repository.PartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartService {

    @Autowired
    private PartRepository partRepository;

    // Create Part
    public Part createPart(Part part) {

        com.fieldservicemanagement.field_service_management.entity.Part entity = new com.fieldservicemanagement.field_service_management.entity.Part();

        entity.setName(part.getName());
        entity.setSku(part.getSku());
        entity.setUnitCost(part.getUnitCost());
        entity.setStockQty(part.getStockQty());

        entity = partRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Part By Id
    public Part getPartById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Part entity = partRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Part not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Parts
    public List<Part> getAllParts() {

        return partRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Part
    public Part updatePart(Long id, Part part) {

        com.fieldservicemanagement.field_service_management.entity.Part entity = partRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Part not found with id : " + id));

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
                        new EntityNotFoundException("Part not found with id : " + id));

        partRepository.delete(entity);
    }

    // Entity -> DTO
    private Part mapToDTO(com.fieldservicemanagement.field_service_management.entity.Part entity) {

        Part dto = new Part();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSku(entity.getSku());
        dto.setUnitCost(entity.getUnitCost());
        dto.setStockQty(entity.getStockQty());

        return dto;
    }
}

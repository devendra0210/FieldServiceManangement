package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.PartDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;
import com.fieldservicemanagement.field_service_management.service.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.PARTS)
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    // Create Part
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<PartDTO> createPart(@RequestBody @Valid PartDTO partDTO) {

        PartDTO createdPart = partService.createPart(partDTO);

        return new ResponseEntity<>(createdPart, HttpStatus.CREATED);
    }

    // Get Part By Id
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_DISPATCHER', 'ROLE_TECHNICIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<PartDTO> getPartById(@PathVariable Long id) {

        PartDTO part = partService.getPartById(id);

        return ResponseEntity.ok(part);
    }

    // Get All Parts
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_DISPATCHER', 'ROLE_TECHNICIAN')")
    @GetMapping
    public ResponseEntity<PageResponse<PartDTO>> getAllParts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection) {

        PageResponse<PartDTO> parts = partService.getPage(page, size, name, sku, sortDirection);

        return ResponseEntity.ok(parts);
    }

    // Update Part
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<PartDTO> updatePart(
            @PathVariable Long id,
            @RequestBody @Valid PartDTO partDTO) {

        PartDTO updatedPart = partService.updatePart(id, partDTO);

        return ResponseEntity.ok(updatedPart);
    }

    // Delete Part
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {

        partService.deletePart(id);

        return ResponseEntity.noContent().build();
    }
}
package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.PartUsageDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;
import com.fieldservicemanagement.field_service_management.service.PartUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.PART_USAGE)
@RequiredArgsConstructor
public class PartUsageController {

    private final PartUsageService partUsageService;

    // Create Part Usage
    @PreAuthorize("hasAnyRole('ROLE_TECHNICIAN')")
    @PostMapping
    public ResponseEntity<PartUsageDTO> createPartUsage(@RequestBody @Valid PartUsageDTO partUsageDTO) {

        PartUsageDTO createdPartUsage = partUsageService.createPartUsage(partUsageDTO);

        return new ResponseEntity<>(createdPartUsage, HttpStatus.CREATED);
    }

    // Get Part Usage By Id
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_TECHNICIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<PartUsageDTO> getPartUsageById(@PathVariable Long id) {

        PartUsageDTO partUsage = partUsageService.getPartUsageById(id);

        return ResponseEntity.ok(partUsage);
    }

    // Get All Part Usages
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_TECHNICIAN')")
    @GetMapping
    public ResponseEntity<PageResponse<PartUsageDTO>> getAllPartUsage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long workOrderId,
            @RequestParam(required = false) Long partId,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection) {

        PageResponse<PartUsageDTO> parts =
                partUsageService.getPage(page, size, workOrderId, partId, sortDirection);

        return ResponseEntity.ok(parts);
    }

    // Update Part Usage
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<PartUsageDTO> updatePartUsage(
            @PathVariable Long id,
            @RequestBody @Valid PartUsageDTO partUsageDTO) {

        PartUsageDTO updatedPartUsage =
                partUsageService.updatePartUsage(id, partUsageDTO);

        return ResponseEntity.ok(updatedPartUsage);
    }

    // Delete Part Usage
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartUsage(@PathVariable Long id) {

        partUsageService.deletePartUsage(id);

        return ResponseEntity.noContent().build();
    }
}

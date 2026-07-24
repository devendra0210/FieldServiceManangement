package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.dto.PartUsageDTO;
import com.fieldservicemanagement.field_service_management.service.PartUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.PART_USAGE)
@CrossOrigin(origins = "*")
public class PartUsageController {

    @Autowired
    private PartUsageService partUsageService;

    // Create Part Usage
    @PostMapping
    public ResponseEntity<PartUsageDTO> createPartUsage(@RequestBody PartUsageDTO partUsageDTO) {

        PartUsageDTO createdPartUsage = partUsageService.createPartUsage(partUsageDTO);

        return new ResponseEntity<>(createdPartUsage, HttpStatus.CREATED);
    }

    // Get Part Usage By Id
    @GetMapping("/{id}")
    public ResponseEntity<PartUsageDTO> getPartUsageById(@PathVariable Long id) {

        PartUsageDTO partUsage = partUsageService.getPartUsageById(id);

        return ResponseEntity.ok(partUsage);
    }

    // Get All Part Usages
    @GetMapping
    public ResponseEntity<List<PartUsageDTO>> getAllPartUsages() {

        List<PartUsageDTO> partUsages = partUsageService.getAllPartUsages();

        return ResponseEntity.ok(partUsages);
    }

    // Update Part Usage
    @PutMapping("/{id}")
    public ResponseEntity<PartUsageDTO> updatePartUsage(
            @PathVariable Long id,
            @RequestBody PartUsageDTO partUsageDTO) {

        PartUsageDTO updatedPartUsage =
                partUsageService.updatePartUsage(id, partUsageDTO);

        return ResponseEntity.ok(updatedPartUsage);
    }

    // Delete Part Usage
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartUsage(@PathVariable Long id) {

        partUsageService.deletePartUsage(id);

        return ResponseEntity.noContent().build();
    }
}

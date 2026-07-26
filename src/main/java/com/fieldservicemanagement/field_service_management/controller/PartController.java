package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.PartDTO;
import com.fieldservicemanagement.field_service_management.service.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.PARTS)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    // Create Part
    @PostMapping
    public ResponseEntity<PartDTO> createPart(@RequestBody @Valid PartDTO partDTO) {

        PartDTO createdPart = partService.createPart(partDTO);

        return new ResponseEntity<>(createdPart, HttpStatus.CREATED);
    }

    // Get Part By Id
    @GetMapping("/{id}")
    public ResponseEntity<PartDTO> getPartById(@PathVariable Long id) {

        PartDTO part = partService.getPartById(id);

        return ResponseEntity.ok(part);
    }

    // Get All Parts
    @GetMapping
    public ResponseEntity<List<PartDTO>> getAllParts() {

        List<PartDTO> parts = partService.getAllParts();

        return ResponseEntity.ok(parts);
    }

    // Update Part
    @PutMapping("/{id}")
    public ResponseEntity<PartDTO> updatePart(
            @PathVariable Long id,
            @RequestBody @Valid PartDTO partDTO) {

        PartDTO updatedPart = partService.updatePart(id, partDTO);

        return ResponseEntity.ok(updatedPart);
    }

    // Delete Part
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {

        partService.deletePart(id);

        return ResponseEntity.noContent().build();
    }
}
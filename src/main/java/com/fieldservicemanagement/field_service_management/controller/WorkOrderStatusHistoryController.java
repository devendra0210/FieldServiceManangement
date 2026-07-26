package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderStatusHistoryDTO;
import com.fieldservicemanagement.field_service_management.service.WorkOrderStatusHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.WORK_ORDER_STATUS_HISTORY)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WorkOrderStatusHistoryController {

    private final WorkOrderStatusHistoryService workOrderStatusHistoryService;

    // Create Status History
    @PostMapping
    public ResponseEntity<WorkOrderStatusHistoryDTO> createHistory(
            @RequestBody @Valid WorkOrderStatusHistoryDTO historyDTO) {

        WorkOrderStatusHistoryDTO createdHistory =
                workOrderStatusHistoryService.createHistory(historyDTO);

        return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<WorkOrderStatusHistoryDTO>> getAllHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<WorkOrderStatusHistoryDTO> history =
                workOrderStatusHistoryService.getAllHistory(
                        page, size, sortBy, sortDir);

        return ResponseEntity.ok(history);
    }

    // Get Status History By Id
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderStatusHistoryDTO> getHistoryById(
            @PathVariable Long id) {

        WorkOrderStatusHistoryDTO history =
                workOrderStatusHistoryService.getHistoryById(id);

        return ResponseEntity.ok(history);
    }

    // Update Status History
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrderStatusHistoryDTO> updateHistory(
            @PathVariable Long id,
            @RequestBody @Valid WorkOrderStatusHistoryDTO historyDTO) {

        WorkOrderStatusHistoryDTO updatedHistory =
                workOrderStatusHistoryService.updateHistory(id, historyDTO);

        return ResponseEntity.ok(updatedHistory);
    }

    // Delete Status History
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {

        workOrderStatusHistoryService.deleteHistory(id);

        return ResponseEntity.noContent().build();
    }
}
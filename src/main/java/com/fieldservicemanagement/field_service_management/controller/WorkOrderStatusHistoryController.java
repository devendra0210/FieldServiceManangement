package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.dto.WorkOrderStatusHistoryDTO;
import com.fieldservicemanagement.field_service_management.service.WorkOrderStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.WORK_ORDER_STATUS_HISTORY)
@CrossOrigin(origins = "*")
public class WorkOrderStatusHistoryController {

    @Autowired
    private WorkOrderStatusHistoryService workOrderStatusHistoryService;

    // Create Status History
    @PostMapping
    public ResponseEntity<WorkOrderStatusHistoryDTO> createHistory(
            @RequestBody WorkOrderStatusHistoryDTO historyDTO) {

        WorkOrderStatusHistoryDTO createdHistory =
                workOrderStatusHistoryService.createHistory(historyDTO);

        return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkOrderStatusHistoryDTO>> getAllHistory() {

        List<WorkOrderStatusHistoryDTO> history =
                workOrderStatusHistoryService.getAllHistory();

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
            @RequestBody WorkOrderStatusHistoryDTO historyDTO) {

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
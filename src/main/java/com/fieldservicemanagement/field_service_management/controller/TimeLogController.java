package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.TimeLogDTO;
import com.fieldservicemanagement.field_service_management.service.TimeLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.TIME_LOG)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TimeLogController {

    private final TimeLogService timeLogService;

    // Create Time Log
    @PostMapping
    public ResponseEntity<TimeLogDTO> createTimeLog(@RequestBody @Valid TimeLogDTO timeLogDTO) {

        TimeLogDTO createdTimeLog = timeLogService.createTimeLog(timeLogDTO);

        return new ResponseEntity<>(createdTimeLog, HttpStatus.CREATED);
    }

    // Get Time Log By Id
    @GetMapping("/{id}")
    public ResponseEntity<TimeLogDTO> getTimeLogById(@PathVariable Long id) {

        TimeLogDTO timeLog = timeLogService.getTimeLogById(id);

        return ResponseEntity.ok(timeLog);
    }

    // Get All Time Logs
    @GetMapping
    public ResponseEntity<Page<TimeLogDTO>> getAllTimeLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<TimeLogDTO> timeLogs =
                timeLogService.getAllTimeLogs(page, size, sortBy, sortDir);

        return ResponseEntity.ok(timeLogs);
    }

    // Update Time Log
    @PutMapping("/{id}")
    public ResponseEntity<TimeLogDTO> updateTimeLog(
            @PathVariable Long id,
            @RequestBody @Valid TimeLogDTO timeLogDTO) {

        TimeLogDTO updatedTimeLog =
                timeLogService.updateTimeLog(id, timeLogDTO);

        return ResponseEntity.ok(updatedTimeLog);
    }

    // Delete Time Log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeLog(@PathVariable Long id) {

        timeLogService.deleteTimeLog(id);

        return ResponseEntity.noContent().build();
    }
}

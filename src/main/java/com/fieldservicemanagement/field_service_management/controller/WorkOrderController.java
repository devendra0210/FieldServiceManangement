package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.service.WorkOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BaseURL.API + BaseURL.WORK_ORDERS)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    // Create Work Order
    @PostMapping
    public ResponseEntity<WorkOrderDTO> createWorkOrder(@RequestBody @Valid WorkOrderDTO workOrderDTO) {

        WorkOrderDTO createdWorkOrder = workOrderService.createWorkOrder(workOrderDTO);

        return new ResponseEntity<>(createdWorkOrder, HttpStatus.CREATED);
    }

    // Get Work Order By Id
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderDTO> getWorkOrderById(@PathVariable Long id) {

        WorkOrderDTO workOrder = workOrderService.getWorkOrderById(id);

        return ResponseEntity.ok(workOrder);
    }

    // Get All Work Orders
    @GetMapping
    public ResponseEntity<PageResponse<WorkOrderDTO>> getAllWorkOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title) {

        PageResponse<WorkOrderDTO> parts =
                workOrderService.getPage(page, size, code, title);

        return ResponseEntity.ok(parts);
    }

    // Update Work Order
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrderDTO> updateWorkOrder(
            @PathVariable Long id,
            @RequestBody @Valid WorkOrderDTO workOrderDTO) {

        WorkOrderDTO updatedWorkOrder =
                workOrderService.updateWorkOrder(id, workOrderDTO);

        return ResponseEntity.ok(updatedWorkOrder);
    }

    // Delete Work Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable Long id) {

        workOrderService.deleteWorkOrder(id);

        return ResponseEntity.noContent().build();
    }
}

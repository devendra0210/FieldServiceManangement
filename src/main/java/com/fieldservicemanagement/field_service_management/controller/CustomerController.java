package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.CustomerDTO;
import com.fieldservicemanagement.field_service_management.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.CUSTOMERS)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Create Customer
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customer) {

        CustomerDTO createdCustomer = customerService.createCustomer(customer);

        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    // Get Customer By Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {

        CustomerDTO customer = customerService.getCustomerById(id);

        return ResponseEntity.ok(customer);
    }

    // Get All Customers
    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<CustomerDTO> customers =
                customerService.getAllCustomers(page, size, sortBy, sortDir);

        return ResponseEntity.ok(customers);
    }

    // Update Customer
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long id,
            @RequestBody @Valid CustomerDTO customer) {

        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customer);

        return ResponseEntity.ok(updatedCustomer);
    }

    // Delete Customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

}

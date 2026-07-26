package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.CustomerDTO;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.CustomerRepository;
import com.fieldservicemanagement.field_service_management.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    // Create Customer
    public CustomerDTO createCustomer(CustomerDTO customer) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = new com.fieldservicemanagement.field_service_management.entity.Customer();

        entity.setName(customer.getName());
        entity.setContactEmail(customer.getContactEmail());

        entity = customerRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Customer By Id
    public CustomerDTO getCustomerById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Customer not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Customers
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Customer
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Customer not found with id : " + id));

        entity.setName(customer.getName());
        entity.setContactEmail(customer.getContactEmail());

        entity = customerRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Customer
    public void deleteCustomer(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Customer not found with id : " + id));

        customerRepository.delete(entity);
    }

    // Entity -> DTO
    private CustomerDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.Customer entity) {

        CustomerDTO dto = new CustomerDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setContactEmail(entity.getContactEmail());

        return dto;
    }
}
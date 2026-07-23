package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.Customer;
import com.fieldservicemanagement.field_service_management.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Create Customer
    public Customer createCustomer(Customer customer) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = new com.fieldservicemanagement.field_service_management.entity.Customer();

        entity.setName(customer.getName());
        entity.setContactEmail(customer.getContactEmail());

        entity = customerRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Customer By Id
    public Customer getCustomerById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = customerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Customers
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Customer
    public Customer updateCustomer(Long id, Customer customer) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = customerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found with id : " + id));

        entity.setName(customer.getName());
        entity.setContactEmail(customer.getContactEmail());

        entity = customerRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Customer
    public void deleteCustomer(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Customer entity = customerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found with id : " + id));

        customerRepository.delete(entity);
    }

    // Entity -> DTO
    private Customer mapToDTO(com.fieldservicemanagement.field_service_management.entity.Customer entity) {

        Customer dto = new Customer();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setContactEmail(entity.getContactEmail());

        return dto;
    }
}
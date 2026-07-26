package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.CustomerDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customer);

    CustomerDTO getCustomerById(Long id);

    Page<CustomerDTO> getAllCustomers(
            int page,
            int size,
            String sortBy,
            String sortDir);

    CustomerDTO updateCustomer(Long id, CustomerDTO customer);

    void deleteCustomer(Long id);

}

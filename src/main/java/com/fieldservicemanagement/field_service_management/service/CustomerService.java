package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.CustomerDTO;
import com.fieldservicemanagement.field_service_management.common.dto.UsersDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.entity.Site;
import com.fieldservicemanagement.field_service_management.entity.WorkOrder;
import com.fieldservicemanagement.field_service_management.enums.RoleName;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customer);

    CustomerDTO getCustomerById(Long id);

    PageResponse<CustomerDTO> getPage(int page, int size, String name, String contactEmail);

    CustomerDTO updateCustomer(Long id, CustomerDTO customer);

    void deleteCustomer(Long id);

}

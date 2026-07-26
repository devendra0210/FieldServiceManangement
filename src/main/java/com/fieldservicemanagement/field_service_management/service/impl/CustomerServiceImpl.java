package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.CustomerDTO;
import com.fieldservicemanagement.field_service_management.common.dto.UsersDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.config.helper.Utils;
import com.fieldservicemanagement.field_service_management.entity.Customer;
import com.fieldservicemanagement.field_service_management.entity.Site;
import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.entity.WorkOrder;
import com.fieldservicemanagement.field_service_management.enums.RoleName;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.CustomerRepository;
import com.fieldservicemanagement.field_service_management.service.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public PageResponse<CustomerDTO> getPage(int page, int size, String name, String contactEmail) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> root = cq.from(Customer.class);

        Predicate predicate = getPredicateList(cb, root, name, contactEmail);

        cq.select(root);
        cq.where(predicate);
        cq.orderBy(cb.desc(root.get("id")));

        List<CustomerDTO> content = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Long count = getCount(cb, entityManager, name, contactEmail);

        if (content.isEmpty()) {
            return PageResponse.defaultPage();
        }

        return new PageResponse<>(page, size, count, content);
    }

    public Predicate getPredicateList(CriteriaBuilder cb, Root<Customer> root, String name, String contactEmail) {

        List<Predicate> predicateList = new ArrayList<>();

        if (Utils.isPresent(name)) {
            predicateList.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (Utils.isPresent(contactEmail)) {
            predicateList.add(cb.like(root.get("email"), "%" + contactEmail + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    public Long getCount(CriteriaBuilder cb, EntityManager entityManager, String name, String contactEmail) {

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Customer> root = query.from(Customer.class);

        Predicate newPredicate = this.getPredicateList(cb, root, name, contactEmail);
        query.select(cb.count(root)).where(newPredicate);

        return entityManager.createQuery(query).getSingleResult();
    }

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
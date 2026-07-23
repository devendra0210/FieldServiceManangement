package com.fieldservicemanagement.field_service_management.repository;

import com.fieldservicemanagement.field_service_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByContactEmail(String contactEmail);

    boolean existsByContactEmail(String contactEmail);

    boolean existsByName(String name);
}
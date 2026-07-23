package com.fieldservicemanagement.field_service_management.repository;

import com.fieldservicemanagement.field_service_management.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    List<Site> findByCustomerId(Long customerId);

    List<Site> findByNameContainingIgnoreCase(String name);
}

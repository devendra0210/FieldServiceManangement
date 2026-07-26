package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.SiteDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SiteService {

    SiteDTO createSite(SiteDTO site);

    SiteDTO getSiteById(Long id);

    Page<SiteDTO> getAllSites(
            int page,
            int size,
            String sortBy,
            String sortDir);

    Page<SiteDTO> getSitesByCustomer(
            Long customerId,
            int page,
            int size,
            String sortBy,
            String sortDir);

    SiteDTO updateSite(Long id, SiteDTO site);

    void deleteSite(Long id);

}

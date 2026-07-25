package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.SiteDTO;

import java.util.List;

public interface SiteService {

    SiteDTO createSite(SiteDTO site);

    SiteDTO getSiteById(Long id);

    List<SiteDTO> getAllSites();

    List<SiteDTO> getSitesByCustomer(Long customerId);

    SiteDTO updateSite(Long id, SiteDTO site);

    void deleteSite(Long id);

}

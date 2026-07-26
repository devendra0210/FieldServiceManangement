package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.SiteDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;


public interface SiteService {

    SiteDTO createSite(SiteDTO site);

    SiteDTO getSiteById(Long id);

    PageResponse<SiteDTO> getPage(int page, int size, String name, Long customerId);

    SiteDTO updateSite(Long id, SiteDTO site);

    void deleteSite(Long id);

}

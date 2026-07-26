package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.SiteDTO;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.CustomerRepository;
import com.fieldservicemanagement.field_service_management.repository.SiteRepository;
import com.fieldservicemanagement.field_service_management.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final CustomerRepository customerRepository;

    // Create Site
    public SiteDTO createSite(SiteDTO site) {

        com.fieldservicemanagement.field_service_management.entity.Customer customer = customerRepository.findById(site.getCustomerId())
                .orElseThrow(() ->
                        new CustomNotFoundException("Customer not found with id : " + site.getCustomerId()));

        com.fieldservicemanagement.field_service_management.entity.Site entity = new com.fieldservicemanagement.field_service_management.entity.Site();

        entity.setName(site.getName());
        entity.setAddress(site.getAddress());
        entity.setCustomer(customer);

        entity = siteRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Site By Id
    public SiteDTO getSiteById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Site entity = siteRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Site not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Sites
    public List<SiteDTO> getAllSites() {

        return siteRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Sites By Customer
    public List<SiteDTO> getSitesByCustomer(Long customerId) {

        return siteRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Site
    public SiteDTO updateSite(Long id, SiteDTO site) {

        com.fieldservicemanagement.field_service_management.entity.Site entity = siteRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Site not found with id : " + id));

        com.fieldservicemanagement.field_service_management.entity.Customer customer = customerRepository.findById(site.getCustomerId())
                .orElseThrow(() ->
                        new CustomNotFoundException("Customer not found with id : " + site.getCustomerId()));

        entity.setName(site.getName());
        entity.setAddress(site.getAddress());
        entity.setCustomer(customer);

        entity = siteRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Site
    public void deleteSite(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Site entity = siteRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Site not found with id : " + id));

        siteRepository.delete(entity);
    }

    // Entity -> DTO
    private SiteDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.Site entity) {

        SiteDTO dto = new SiteDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getId());
        }

        return dto;
    }
}

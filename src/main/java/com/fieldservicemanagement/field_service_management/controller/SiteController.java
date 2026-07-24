package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.dto.SiteDTO;
import com.fieldservicemanagement.field_service_management.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.SITES)
@CrossOrigin(origins = "*")
public class SiteController {

    @Autowired
    private SiteService siteService;

    // Create Site
    @PostMapping
    public ResponseEntity<SiteDTO> createSite(@RequestBody SiteDTO siteDTO) {

        SiteDTO createdSite = siteService.createSite(siteDTO);

        return new ResponseEntity<>(createdSite, HttpStatus.CREATED);
    }

    // Get Site By Id
    @GetMapping("/{id}")
    public ResponseEntity<SiteDTO> getSiteById(@PathVariable Long id) {

        SiteDTO site = siteService.getSiteById(id);

        return ResponseEntity.ok(site);
    }

    // Get All Sites
    @GetMapping
    public ResponseEntity<List<SiteDTO>> getAllSites() {

        List<SiteDTO> sites = siteService.getAllSites();

        return ResponseEntity.ok(sites);
    }

    // Get Sites By Customer
    @GetMapping(BaseURL.CUSTOMERS + "/{customerId}")
    public ResponseEntity<List<SiteDTO>> getSitesByCustomer(@PathVariable Long customerId) {

        List<SiteDTO> sites = siteService.getSitesByCustomer(customerId);

        return ResponseEntity.ok(sites);
    }

    // Update Site
    @PutMapping("/{id}")
    public ResponseEntity<SiteDTO> updateSite(
            @PathVariable Long id,
            @RequestBody SiteDTO siteDTO) {

        SiteDTO updatedSite = siteService.updateSite(id, siteDTO);

        return ResponseEntity.ok(updatedSite);
    }

    // Delete Site
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSite(@PathVariable Long id) {

        siteService.deleteSite(id);

        return ResponseEntity.noContent().build();
    }
}

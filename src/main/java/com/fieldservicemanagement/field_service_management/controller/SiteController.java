package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.dto.SiteDTO;
import com.fieldservicemanagement.field_service_management.service.SiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.SITES)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    // Create Site
    @PostMapping
    public ResponseEntity<SiteDTO> createSite(@RequestBody @Valid SiteDTO siteDTO) {

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
    public ResponseEntity<Page<SiteDTO>> getAllSites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<SiteDTO> sites =
                siteService.getAllSites(page, size, sortBy, sortDir);

        return ResponseEntity.ok(sites);
    }

    // Get Sites By Customer
    @GetMapping(BaseURL.CUSTOMERS + "/{customerId}")
    public ResponseEntity<Page<SiteDTO>> getSitesByCustomer(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<SiteDTO> sites = siteService.getSitesByCustomer(
                customerId, page, size, sortBy, sortDir);

        return ResponseEntity.ok(sites);
    }

    // Update Site
    @PutMapping("/{id}")
    public ResponseEntity<SiteDTO> updateSite(
            @PathVariable Long id,
            @RequestBody @Valid SiteDTO siteDTO) {

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

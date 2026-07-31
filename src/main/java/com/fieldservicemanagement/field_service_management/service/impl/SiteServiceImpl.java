package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.SiteDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.config.helper.Utils;
import com.fieldservicemanagement.field_service_management.entity.Customer;
import com.fieldservicemanagement.field_service_management.entity.Site;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.CustomerRepository;
import com.fieldservicemanagement.field_service_management.repository.SiteRepository;
import com.fieldservicemanagement.field_service_management.service.SiteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final CustomerRepository customerRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    // Create Site
    public SiteDTO createSite(SiteDTO site) {

        Customer customer = customerRepository.findById(site.getCustomerId())
                .orElseThrow(() ->
                        new CustomNotFoundException("Customer not found with id : " + site.getCustomerId()));

        Site entity = new Site();

        entity.setName(site.getName());
        entity.setAddress(site.getAddress());
        entity.setCustomer(customer);

        entity = siteRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Site By Id
    public SiteDTO getSiteById(Long id) {

        Site entity = siteRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Site not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Sites
    @Override
    public PageResponse<SiteDTO> getPage(int page, int size, String name, Long customerId, SortDirection sortDirection) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Site> cq = cb.createQuery(Site.class);

        Root<Site> root = cq.from(Site.class);

        Predicate predicate = getPredicateList(cb, root, name, customerId);

        cq.select(root);
        cq.where(predicate);

        if (sortDirection.isAscending()) {
            cq.orderBy(cb.asc(root.get("id")));
        } else {
            cq.orderBy(cb.desc(root.get("id")));
        }

        List<SiteDTO> content = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Long count = getCount(cb, entityManager, name, customerId);

        if (content.isEmpty()) {
            return PageResponse.defaultPage();
        }

        return new PageResponse<>(page, size, count, content);
    }

    public Predicate getPredicateList(CriteriaBuilder cb, Root<Site> root, String name, Long customerId) {

        List<Predicate> predicateList = new ArrayList<>();

        if (Utils.isPresent(name)) {
            predicateList.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (Utils.isPresent(customerId)) {
            predicateList.add(cb.like(root.get("customerId"), "%" + customerId + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    public Long getCount(CriteriaBuilder cb, EntityManager entityManager, String name, Long customerId) {

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Site> root = query.from(Site.class);

        Predicate newPredicate = this.getPredicateList(cb, root, name, customerId);
        query.select(cb.count(root)).where(newPredicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    // Update Site
    public SiteDTO updateSite(Long id, SiteDTO site) {

        Site entity = siteRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Site not found with id : " + id));

        Customer customer = customerRepository.findById(site.getCustomerId())
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

        Site entity = siteRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Site not found with id : " + id));

        siteRepository.delete(entity);
    }

    // Entity -> DTO
    private SiteDTO mapToDTO(Site entity) {

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

package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.PartDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.config.helper.Utils;
import com.fieldservicemanagement.field_service_management.entity.Part;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.PartRepository;
import com.fieldservicemanagement.field_service_management.service.PartService;
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
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    // Create Part
    public PartDTO createPart(PartDTO part) {

        Part entity = new Part();

        entity.setName(part.getName());
        entity.setSku(part.getSku());
        entity.setUnitCost(part.getUnitCost());
        entity.setStockQty(part.getStockQty());

        entity = partRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Part By Id
    public PartDTO getPartById(Long id) {

        Part entity = partRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Part not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Parts
    @Override
    public PageResponse<PartDTO> getPage(int page, int size, String name, String sku, SortDirection sortDirection) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Part> cq = cb.createQuery(Part.class);

        Root<Part> root = cq.from(Part.class);

        Predicate predicate = getPredicateList(cb, root, name, sku);

        cq.select(root);
        cq.where(predicate);

        if (sortDirection.isAscending()) {
            cq.orderBy(cb.asc(root.get("id")));
        } else {
            cq.orderBy(cb.desc(root.get("id")));
        }

        List<PartDTO> content = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Long count = getCount(cb, entityManager, name, sku);

        if (content.isEmpty()) {
            return PageResponse.defaultPage();
        }

        return new PageResponse<>(page, size, count, content);
    }

    public Predicate getPredicateList(CriteriaBuilder cb, Root<Part> root, String name, String sku) {

        List<Predicate> predicateList = new ArrayList<>();

        if (Utils.isPresent(name)) {
            predicateList.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (Utils.isPresent(sku)) {
            predicateList.add(cb.like(root.get("sku"), "%" + sku + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    public Long getCount(CriteriaBuilder cb, EntityManager entityManager, String name, String sku) {

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Part> root = query.from(Part.class);

        Predicate newPredicate = this.getPredicateList(cb, root, name, sku);
        query.select(cb.count(root)).where(newPredicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    // Update Part
    public PartDTO updatePart(Long id, PartDTO part) {

        Part entity = partRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Part not found with id : " + id));

        entity.setName(part.getName());
        entity.setSku(part.getSku());
        entity.setUnitCost(part.getUnitCost());
        entity.setStockQty(part.getStockQty());

        entity = partRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Part
    public void deletePart(Long id) {

        Part entity = partRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Part not found with id : " + id));

        partRepository.delete(entity);
    }

    // Entity -> DTO
    private PartDTO mapToDTO(Part entity) {

        PartDTO dto = new PartDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSku(entity.getSku());
        dto.setUnitCost(entity.getUnitCost());
        dto.setStockQty(entity.getStockQty());

        return dto;
    }
}

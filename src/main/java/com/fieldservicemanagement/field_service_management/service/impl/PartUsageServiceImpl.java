package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.PartUsageDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.config.helper.Utils;
import com.fieldservicemanagement.field_service_management.entity.Part;
import com.fieldservicemanagement.field_service_management.entity.PartUsage;
import com.fieldservicemanagement.field_service_management.entity.WorkOrder;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.PartRepository;
import com.fieldservicemanagement.field_service_management.repository.PartUsageRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import com.fieldservicemanagement.field_service_management.service.PartUsageService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartUsageServiceImpl implements PartUsageService {

    private final PartUsageRepository partUsageRepository;
    private final PartRepository partRepository;
    private final WorkOrderRepository workOrderRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    // Add Part Usage
    public PartUsageDTO createPartUsage(PartUsageDTO partUsage) {

        WorkOrder workOrder = workOrderRepository
                .findById(partUsage.getWorkOrderId())
                .orElseThrow(() -> new CustomNotFoundException("Work Order not found"));

        Part part = partRepository
                .findById(partUsage.getPartId())
                .orElseThrow(() -> new CustomNotFoundException("Part not found"));

        if (part.getStockQty() < partUsage.getQtyUsed()) {
            throw new RuntimeException("Insufficient stock available.");
        }

        // Reduce Stock
        part.setStockQty(part.getStockQty() - partUsage.getQtyUsed());
        partRepository.save(part);

        PartUsage entity = new PartUsage();

        entity.setWorkOrder(workOrder);
        entity.setPart(part);
        entity.setQtyUsed(partUsage.getQtyUsed());

        entity = partUsageRepository.save(entity);

        return mapToDTO(entity);
    }

    @Override
    public PageResponse<PartUsageDTO> getPage(int page, int size, Long workOrderId, Long partId, SortDirection sortDirection) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PartUsage> cq = cb.createQuery(PartUsage.class);

        Root<PartUsage> root = cq.from(PartUsage.class);

        Predicate predicate = getPredicateList(cb, root, workOrderId, partId);

        cq.select(root);
        cq.where(predicate);

        if (sortDirection.isAscending()) {
            cq.orderBy(cb.asc(root.get("id")));
        } else {
            cq.orderBy(cb.desc(root.get("id")));
        }

        List<PartUsageDTO> content = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Long count = getCount(cb, entityManager, workOrderId, partId);

        if (content.isEmpty()) {
            return PageResponse.defaultPage();
        }

        return new PageResponse<>(page, size, count, content);
    }

    public Predicate getPredicateList(CriteriaBuilder cb, Root<PartUsage> root, Long workOrderId, Long partId) {

        List<Predicate> predicateList = new ArrayList<>();

        if (Utils.isPresent(workOrderId)) {
            predicateList.add(cb.like(root.get("workOrderId"), "%" + workOrderId + "%"));
        }

        if (Utils.isPresent(partId)) {
            predicateList.add(cb.like(root.get("partId"), "%" + partId + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    public Long getCount(CriteriaBuilder cb, EntityManager entityManager,Long workOrderId, Long partId) {

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<PartUsage> root = query.from(PartUsage.class);

        Predicate newPredicate = this.getPredicateList(cb, root, workOrderId, partId);
        query.select(cb.count(root)).where(newPredicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    // Get Usage By Id
    public PartUsageDTO getPartUsageById(Long id) {

        PartUsage entity = partUsageRepository.findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("Part Usage not found"));

        return mapToDTO(entity);
    }

    // Get Usage By Work Order
    public List<PartUsageDTO> getPartUsageByWorkOrder(Long workOrderId) {

        return partUsageRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Update usage

    public PartUsageDTO updatePartUsage(Long id, PartUsageDTO partUsageDTO) {

        PartUsage entity = partUsageRepository.findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("Part Usage not found"));

        WorkOrder workOrder = workOrderRepository.findById(partUsageDTO.getWorkOrderId())
                        .orElseThrow(() -> new CustomNotFoundException("Work Order not found"));

        Part part = partRepository.findById(partUsageDTO.getPartId())
                        .orElseThrow(() -> new CustomNotFoundException("Part not found"));

        entity.setWorkOrder(workOrder);
        entity.setPart(part);
        entity.setQtyUsed(partUsageDTO.getQtyUsed());

        entity = partUsageRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Usage
    public void deletePartUsage(Long id) {

        PartUsage entity = partUsageRepository.findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("Part Usage not found"));

        // Restore Stock
        Part part = entity.getPart();

        part.setStockQty(part.getStockQty() + entity.getQtyUsed());
        partRepository.save(part);

        partUsageRepository.delete(entity);
    }

    // Entity -> DTO
    private PartUsageDTO mapToDTO(PartUsage entity) {

        PartUsageDTO dto = new PartUsageDTO();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setPartId(entity.getPart().getId());
        dto.setQtyUsed(entity.getQtyUsed());

        return dto;
    }
}

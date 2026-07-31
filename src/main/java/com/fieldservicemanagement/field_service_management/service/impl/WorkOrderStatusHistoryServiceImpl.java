package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.WorkOrderStatusHistoryDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.config.helper.Utils;
import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.entity.WorkOrder;
import com.fieldservicemanagement.field_service_management.entity.WorkOrderStatusHistory;
import com.fieldservicemanagement.field_service_management.enums.SortDirection;
import com.fieldservicemanagement.field_service_management.enums.WorkStatus;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderStatusHistoryRepository;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.service.WorkOrderStatusHistoryService;
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
public class WorkOrderStatusHistoryServiceImpl implements WorkOrderStatusHistoryService {

    private final WorkOrderStatusHistoryRepository historyRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UsersRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    // Create Status History
    public WorkOrderStatusHistoryDTO createHistory(WorkOrderStatusHistoryDTO history) {
        WorkOrder workOrder = workOrderRepository
                .findById(history.getWorkOrderId())
                .orElseThrow(() -> new CustomNotFoundException("Work Order not found"));

        Users user = userRepository
                .findById(Long.valueOf(history.getChangedBy()))
                .orElseThrow(() -> new CustomNotFoundException("User not found"));

        WorkOrderStatusHistory entity = new WorkOrderStatusHistory();

        entity.setWorkOrder(workOrder);
        entity.setFromStatus(history.getFromStatus());
        entity.setToStatus(history.getToStatus());
        entity.setChangedBy(user);
        entity.setChangedAt(history.getChangedAt());

        entity = historyRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get History By Id
    public WorkOrderStatusHistoryDTO getHistoryById(Long id) {
        WorkOrderStatusHistory entity = historyRepository.findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("History not found"));

        return mapToDTO(entity);
    }

    // Get All History

    @Override
    public PageResponse<WorkOrderStatusHistoryDTO> getPage(int page, int size, Long workOrderId, SortDirection sortDirection) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkOrderStatusHistory> cq = cb.createQuery(WorkOrderStatusHistory.class);

        Root<WorkOrderStatusHistory> root = cq.from(WorkOrderStatusHistory.class);

        Predicate predicate = getPredicateList(cb, root, workOrderId);

        cq.select(root);
        cq.where(predicate);

        if (sortDirection.isAscending()) {
            cq.orderBy(cb.asc(root.get("id")));
        } else {
            cq.orderBy(cb.desc(root.get("id")));
        }

        List<WorkOrderStatusHistoryDTO> content = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Long count = getCount(cb, entityManager, workOrderId);

        if (content.isEmpty()) {
            return PageResponse.defaultPage();
        }

        return new PageResponse<>(page, size, count, content);
    }

    public Predicate getPredicateList(CriteriaBuilder cb, Root<WorkOrderStatusHistory> root, Long workOrderId) {

        List<Predicate> predicateList = new ArrayList<>();

        if (Utils.isPresent(workOrderId)) {
            predicateList.add(cb.like(root.get("workOrderId"), "%" + workOrderId + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    public Long getCount(CriteriaBuilder cb, EntityManager entityManager, Long workOrderId) {

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<WorkOrderStatusHistory> root = query.from(WorkOrderStatusHistory.class);

        Predicate newPredicate = this.getPredicateList(cb, root, workOrderId);
        query.select(cb.count(root)).where(newPredicate);

        return entityManager.createQuery(query).getSingleResult();
    }
    // Delete History
    public void deleteHistory(Long id) {
        WorkOrderStatusHistory entity = historyRepository.findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("History not found"));

        historyRepository.delete(entity);
    }

    // Update Status History
    public WorkOrderStatusHistoryDTO updateHistory(Long id, WorkOrderStatusHistoryDTO historyDTO) {

        WorkOrderStatusHistory entity = historyRepository
                .findById(id)
                .orElseThrow(() -> new CustomNotFoundException("History not found"));

        WorkOrder workOrder = workOrderRepository
                .findById(historyDTO.getWorkOrderId())
                .orElseThrow(() -> new CustomNotFoundException("Work Order not found"));

        Users user = userRepository
                .findById(Long.valueOf(historyDTO.getChangedBy()))
                .orElseThrow(() -> new CustomNotFoundException("User not found"));

        entity.setWorkOrder(workOrder);
        entity.setFromStatus(historyDTO.getFromStatus());
        entity.setToStatus(historyDTO.getToStatus());
        entity.setChangedBy(user);
        entity.setChangedAt(historyDTO.getChangedAt());

        entity = historyRepository.save(entity);

        return mapToDTO(entity);
    }

    // Entity -> DTO
    private WorkOrderStatusHistoryDTO mapToDTO(WorkOrderStatusHistory entity) {

        WorkOrderStatusHistoryDTO dto = new WorkOrderStatusHistoryDTO();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setFromStatus(entity.getFromStatus());
        dto.setToStatus(entity.getToStatus());

        if (entity.getChangedBy() != null) {
            dto.setChangedBy(String.valueOf(entity.getChangedBy().getId()));
        }

        dto.setChangedAt(entity.getChangedAt());

        return dto;
    }
}

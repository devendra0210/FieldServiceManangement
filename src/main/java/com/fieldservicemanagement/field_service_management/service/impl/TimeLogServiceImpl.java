package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.dto.TimeLogDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.config.helper.Utils;
import com.fieldservicemanagement.field_service_management.entity.TimeLog;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.TimeLogRepository;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.repository.WorkOrderRepository;
import com.fieldservicemanagement.field_service_management.service.TimeLogService;
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
public class TimeLogServiceImpl implements TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final WorkOrderRepository workOrderRepository;
    private final UsersRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    // Create Time Log
    public TimeLogDTO createTimeLog(TimeLogDTO timeLog) {

        com.fieldservicemanagement.field_service_management.entity.WorkOrder workOrder = workOrderRepository
                .findById(timeLog.getWorkOrderId())
                .orElseThrow(() ->
                        new CustomNotFoundException("Work Order not found"));

        com.fieldservicemanagement.field_service_management.entity.Users technician = userRepository
                .findById(timeLog.getTechnicianId())
                .orElseThrow(() ->
                        new CustomNotFoundException("Technician not found"));

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity =
                new com.fieldservicemanagement.field_service_management.entity.TimeLog();

        entity.setWorkOrder(workOrder);
        entity.setTechnician(technician);
        entity.setMinutes(timeLog.getMinutes());
        entity.setNote(timeLog.getNote());

        entity = timeLogRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get Time Log By Id
    public TimeLogDTO getTimeLogById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity = timeLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Time Log not found"));

        return mapToDTO(entity);
    }

    // Get All Time Logs
    @Override
    public PageResponse<TimeLogDTO> getPage(int page, int size, Long workOrderId, Long technicianId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TimeLog> cq = cb.createQuery(TimeLog.class);

        Root<TimeLog> root = cq.from(TimeLog.class);

        Predicate predicate = getPredicateList(cb, root, workOrderId, technicianId);

        cq.select(root);
        cq.where(predicate);
        cq.orderBy(cb.desc(root.get("id")));

        List<TimeLogDTO> content = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Long count = getCount(cb, entityManager, workOrderId, technicianId);

        if (content.isEmpty()) {
            return PageResponse.defaultPage();
        }

        return new PageResponse<>(page, size, count, content);
    }

    public Predicate getPredicateList(CriteriaBuilder cb, Root<TimeLog> root, Long workOrderId, Long technicianId) {

        List<Predicate> predicateList = new ArrayList<>();

        if (Utils.isPresent(workOrderId)) {
            predicateList.add(cb.like(root.get("work_order_id"), "%" + workOrderId + "%"));
        }

        if (Utils.isPresent(technicianId)) {
            predicateList.add(cb.like(root.get("technician_id"), "%" + technicianId + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    public Long getCount(CriteriaBuilder cb, EntityManager entityManager, Long workOrderId, Long technicianId) {

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<TimeLog> root = query.from(TimeLog.class);

        Predicate newPredicate = this.getPredicateList(cb, root, workOrderId, technicianId);
        query.select(cb.count(root)).where(newPredicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    // Get Time Logs By Work Order
    public List<TimeLogDTO> getTimeLogsByWorkOrder(Long workOrderId) {

        return timeLogRepository.findByWorkOrderId(workOrderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Time Logs By Technician
    public List<TimeLogDTO> getTimeLogsByTechnician(Long technicianId) {

        return timeLogRepository.findByTechnicianId(technicianId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Time Log
    public TimeLogDTO updateTimeLog(Long id, TimeLogDTO timeLog) {

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity = timeLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Time Log not found"));

        entity.setMinutes(timeLog.getMinutes());
        entity.setNote(timeLog.getNote());

        entity = timeLogRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete Time Log
    public void deleteTimeLog(Long id) {

        com.fieldservicemanagement.field_service_management.entity.TimeLog entity = timeLogRepository
                .findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("Time Log not found"));

        timeLogRepository.delete(entity);
    }

    // Entity -> DTO
    private TimeLogDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.TimeLog entity) {

        TimeLogDTO dto = new TimeLogDTO();

        dto.setId(entity.getId());
        dto.setWorkOrderId(entity.getWorkOrder().getId());
        dto.setTechnicianId(entity.getTechnician().getId());
        dto.setMinutes(entity.getMinutes());
        dto.setNote(entity.getNote());

        return dto;
    }
}
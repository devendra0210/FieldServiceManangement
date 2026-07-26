package com.fieldservicemanagement.field_service_management.service.impl;

import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.config.helper.Utils;
import com.fieldservicemanagement.field_service_management.common.dto.UsersDTO;
import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.enums.RoleName;
import com.fieldservicemanagement.field_service_management.exception.CustomNotFoundException;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import com.fieldservicemanagement.field_service_management.service.UsersService;
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
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public PageResponse<UsersDTO> getPage(int page, int size, String name, String email, RoleName role) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);

        Root<Users> root = cq.from(Users.class);

        Predicate predicate = getPredicateList(cb, root, name, email, role);

        cq.select(root);
        cq.where(predicate);
        cq.orderBy(cb.desc(root.get("id")));

        List<UsersDTO> content = entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Long count = getCount(cb, entityManager, name, email, role);

        if (content.isEmpty()) {
            return PageResponse.defaultPage();
        }

        return new PageResponse<>(page, size, count, content);
    }

    public Predicate getPredicateList(CriteriaBuilder cb, Root<Users> root, String name, String email, RoleName role) {

        List<Predicate> predicateList = new ArrayList<>();

        if (Utils.isPresent(name)) {
            predicateList.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (Utils.isPresent(email)) {
            predicateList.add(cb.like(root.get("email"), "%" + email + "%"));
        }

        if (Utils.isPresent(role)) {
            predicateList.add(cb.equal(root.get("role"), role.name()));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }

    public Long getCount(CriteriaBuilder cb, EntityManager entityManager, String name, String email, RoleName role) {

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Users> root = query.from(Users.class);

        Predicate newPredicate = this.getPredicateList(cb, root, name, email, role);
        query.select(cb.count(root)).where(newPredicate);

        return entityManager.createQuery(query).getSingleResult();
    }

    // Create User
    public UsersDTO createUser(UsersDTO user) {

        com.fieldservicemanagement.field_service_management.entity.Users entity = new com.fieldservicemanagement.field_service_management.entity.Users();

        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setRole(user.getRole());

        entity = userRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get User By Id
    public UsersDTO getUserById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Users entity = userRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("User not found with id : " + id));

        return mapToDTO(entity);
    }

    // Get All Users
    public List<UsersDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Technicians
    public List<UsersDTO> getTechnicians() {

        return userRepository.findByRole("ROLE_TECHNICAL_SPECIALIST")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update User
    public UsersDTO updateUser(Long id, UsersDTO user) {

        com.fieldservicemanagement.field_service_management.entity.Users entity = userRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("User not found with id : " + id));

        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setRole(user.getRole());

        entity = userRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete User
    public void deleteUser(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Users entity = userRepository.findById(id)
                .orElseThrow(() ->
                        new CustomNotFoundException("User not found with id : " + id));

        userRepository.delete(entity);
    }

    // Entity -> DTO
    private UsersDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.Users entity) {

        UsersDTO dto = new UsersDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());

        return dto;
    }
}
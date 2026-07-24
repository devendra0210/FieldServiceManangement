package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.UsersDTO;
import com.fieldservicemanagement.field_service_management.enums.RoleName;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    // Create User
    public UsersDTO createUser(UsersDTO user) {

        com.fieldservicemanagement.field_service_management.entity.Users entity = new com.fieldservicemanagement.field_service_management.entity.Users();

        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setRole(RoleName.valueOf(user.getRole()));

        entity = userRepository.save(entity);

        return mapToDTO(entity);
    }

    // Get User By Id
    public UsersDTO getUserById(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Users entity = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id : " + id));

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
                        new EntityNotFoundException("User not found with id : " + id));

        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setRole(RoleName.valueOf(user.getRole()));

        entity = userRepository.save(entity);

        return mapToDTO(entity);
    }

    // Delete User
    public void deleteUser(Long id) {

        com.fieldservicemanagement.field_service_management.entity.Users entity = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id : " + id));

        userRepository.delete(entity);
    }

    // Entity -> DTO
    private UsersDTO mapToDTO(com.fieldservicemanagement.field_service_management.entity.Users entity) {

        UsersDTO dto = new UsersDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(String.valueOf(entity.getRole()));

        return dto;
    }
}
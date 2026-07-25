package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.dto.UsersDTO;

import java.util.List;

public interface UsersService {

    UsersDTO createUser(UsersDTO user);

    UsersDTO getUserById(Long id);

    List<UsersDTO> getAllUsers();

    List<UsersDTO> getTechnicians();

    UsersDTO updateUser(Long id, UsersDTO user);

    void deleteUser(Long id);

}
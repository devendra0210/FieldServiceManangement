package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.common.dto.UsersDTO;
import com.fieldservicemanagement.field_service_management.enums.RoleName;

import java.util.List;

public interface UsersService {

    UsersDTO createUser(UsersDTO user);

    UsersDTO getUserById(Long id);

    List<UsersDTO> getAllUsers();

    PageResponse<UsersDTO> getPage(int page, int size, String name, String email, RoleName role);

    List<UsersDTO> getTechnicians();

    UsersDTO updateUser(Long id, UsersDTO user);

    void deleteUser(Long id);

}
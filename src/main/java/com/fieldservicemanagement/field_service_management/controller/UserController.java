package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import com.fieldservicemanagement.field_service_management.common.dto.UsersDTO;
import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.enums.RoleName;
import com.fieldservicemanagement.field_service_management.security.CurrentUser;
import com.fieldservicemanagement.field_service_management.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(BaseURL.API + BaseURL.USER)
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @GetMapping(BaseURL.ME)
    public ResponseEntity<UsersDTO> getMe(@CurrentUser Users user) {
        UsersDTO usersDTO = usersService.getUserById(user.getId());
        return ResponseEntity.ok(usersDTO);
    }

    // Create User
    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody @Valid UsersDTO usersDTO) {

        UsersDTO createdUser = usersService.createUser(usersDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Get User By Id
    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Long id) {

        UsersDTO user = usersService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    // Get Page Users
    @GetMapping(BaseURL.PAGE)
    public ResponseEntity<PageResponse<UsersDTO>> getPageUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false)RoleName role
            ) {
        PageResponse<UsersDTO> pageResponse = usersService.getPage(page, size, name, email, role);

        return ResponseEntity.ok(pageResponse);
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UsersDTO usersDTO) {

        UsersDTO updatedUser = usersService.updateUser(id, usersDTO);

        return ResponseEntity.ok(updatedUser);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        usersService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}

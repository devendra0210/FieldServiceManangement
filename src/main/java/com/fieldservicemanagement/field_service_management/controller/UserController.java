package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import com.fieldservicemanagement.field_service_management.dto.UsersDTO;
import com.fieldservicemanagement.field_service_management.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.API + BaseURL.USER)
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @GetMapping(BaseURL.ME)
    public String getMe() {
        return "Success";
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

    // Get All Users
    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {

        List<UsersDTO> users = usersService.getAllUsers();

        return ResponseEntity.ok(users);
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

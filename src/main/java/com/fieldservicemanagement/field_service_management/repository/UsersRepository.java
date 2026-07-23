package com.fieldservicemanagement.field_service_management.repository;

import com.fieldservicemanagement.field_service_management.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Users> findByRole(String role);

    List<Users> findByNameContainingIgnoreCase(String name);
}

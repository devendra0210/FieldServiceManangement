package com.fieldservicemanagement.field_service_management.config;

import com.fieldservicemanagement.field_service_management.entity.Users;
import com.fieldservicemanagement.field_service_management.enums.RoleName;
import com.fieldservicemanagement.field_service_management.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) {

        if (!ddl.equals("create"))
            return;

        Users admin = Users.builder()
                .name("Administrator")
                .email("admin@example.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .role(RoleName.ROLE_ADMIN)
                .build();

        userRepository.save(admin);
    }
}

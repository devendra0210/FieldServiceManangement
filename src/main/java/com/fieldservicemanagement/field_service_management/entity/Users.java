package com.fieldservicemanagement.field_service_management.entity;

import com.fieldservicemanagement.field_service_management.base.BaseEntity;
import com.fieldservicemanagement.field_service_management.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users extends BaseEntity implements UserDetails {

    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private RoleName role;

    @Column(name = "password_hash")
    private String passwordHash;

    @OneToMany(mappedBy = "assignedTo")
    private List<WorkOrder> assignedWorkOrders;

    @OneToMany(mappedBy = "technician")
    private List<TimeLog> timeLogs;

    @OneToMany(mappedBy = "changedBy")
    private List<WorkOrderStatusHistory> statusHistory;

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public @NonNull String getUsername() {
        return this.email;
    }
}
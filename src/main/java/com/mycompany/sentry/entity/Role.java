package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Roles")
@Data
public class Role {
    @Id
    @Column(name = "RoleID")
    private Integer roleID;

    @Column(name = "RoleName", unique = true, nullable = false)
    private String roleName;
}



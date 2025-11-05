package com.mycompany.sentry.entity;

import com.mycompany.sentry.constant.AppConstants;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name = "RoleID")
    private Integer roleID;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "IsActive")
    private Boolean isActive = true;

    @Column(name = "IsBan")
    private Boolean isBan = false;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "JapaneseLevel")
    private String japaneseLevel;

    @Column(name = "Avatar")
    private String avatar;

    @Column(name = "Gender")
    private String gender = AppConstants.DEFAULT_GENDER;
}
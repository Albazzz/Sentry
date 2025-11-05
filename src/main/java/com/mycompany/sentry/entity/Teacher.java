package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Teacher")
@Data
public class Teacher {
    @Id
    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "TeacherPending")
    private Boolean teacherPending = false;

    @Column(name = "Certificate", length = 500)
    private String certificate;

    @OneToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;
}



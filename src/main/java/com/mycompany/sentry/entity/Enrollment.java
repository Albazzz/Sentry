package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Enrollment")
@Data
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnrollmentID")
    private Integer enrollmentID;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "CourseID")
    private Integer courseID;

    @Column(name = "EnrolledAt")
    private LocalDateTime enrolledAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CourseID", insertable = false, updatable = false)
    private Course course;
}



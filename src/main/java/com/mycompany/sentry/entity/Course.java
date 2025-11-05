package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Courses")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseID")
    private Integer courseID;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "IsHidden")
    private Boolean isHidden = false;

    @Column(name = "IsSuggested")
    private Boolean isSuggested = false;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "imageUrl", columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "CreatedBy", insertable = false, updatable = false)
    private User creator;
}



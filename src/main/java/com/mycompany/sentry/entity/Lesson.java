package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "Lessons")
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LessonID")
    private Integer lessonID;

    @Column(name = "CourseID")
    private Integer courseID;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description", length = 1000)
    private String description;

    @Column(name = "IsHidden")
    private Boolean isHidden = false;

    @Column(name = "StudyStatus")
    private Integer studyStatus = 0;

    @Column(name = "OrderIndex")
    private Integer orderIndex = 0;

    @ManyToOne
    @JoinColumn(name = "CourseID", insertable = false, updatable = false)
    private Course course;
}



package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LessonMaterials")
@Data
public class LessonMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaterialID")
    private Integer materialID;

    @Column(name = "LessonID", nullable = false)
    private Integer lessonID;

    @Column(name = "MaterialType", length = 50, nullable = false)
    private String materialType;

    @Column(name = "FilePath", columnDefinition = "TEXT")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "LessonID", insertable = false, updatable = false)
    private Lesson lesson;
}



package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Vocabulary")
@Data
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VocabID")
    private Integer vocabID;

    @Column(name = "Word", length = 100)
    private String word;

    @Column(name = "Meaning", length = 255)
    private String meaning;

    @Column(name = "Reading", length = 100)
    private String reading;

    @Column(name = "Example", columnDefinition = "TEXT")
    private String example;

    @Column(name = "LessonID")
    private Integer lessonID;

    @Column(name = "imagePath", length = 255)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "LessonID", insertable = false, updatable = false)
    private Lesson lesson;
}



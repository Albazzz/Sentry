package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Kanji")
@Data
public class Kanji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KanjiID")
    private Integer kanjiID;

    @Column(name = "Character", length = 10)
    private String character;

    @Column(name = "Onyomi", length = 100)
    private String onyomi;

    @Column(name = "Kunyomi", length = 100)
    private String kunyomi;

    @Column(name = "Meaning", length = 255)
    private String meaning;

    @Column(name = "LessonID")
    private Integer lessonID;

    @ManyToOne
    @JoinColumn(name = "LessonID", insertable = false, updatable = false)
    private Lesson lesson;
}



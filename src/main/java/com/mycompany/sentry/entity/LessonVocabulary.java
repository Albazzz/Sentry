package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LessonVocabulary")
@Data
@IdClass(LessonVocabularyId.class)
public class LessonVocabulary {
    @Id
    @Column(name = "LessonID")
    private Integer lessonID;

    @Id
    @Column(name = "VocabID")
    private Integer vocabID;

    @ManyToOne
    @JoinColumn(name = "LessonID", insertable = false, updatable = false)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "VocabID", insertable = false, updatable = false)
    private Vocabulary vocabulary;
}




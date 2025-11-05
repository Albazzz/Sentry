package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Quizzes")
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuizID")
    private Integer quizID;

    @Column(name = "LessonID")
    private Integer lessonID;

    @Column(name = "Title", length = 255)
    private String title;

    @ManyToOne
    @JoinColumn(name = "LessonID", insertable = false, updatable = false)
    private Lesson lesson;
}



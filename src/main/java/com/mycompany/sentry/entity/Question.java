package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuestionID")
    private Integer questionID;

    @Column(name = "QuizID")
    private Integer quizID;

    @Column(name = "QuestionText", columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "TimeLimit")
    private Integer timeLimit = 30;

    @ManyToOne
    @JoinColumn(name = "QuizID", insertable = false, updatable = false)
    private Quiz quiz;
}



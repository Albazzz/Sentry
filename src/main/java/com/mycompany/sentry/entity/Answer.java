package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Answers")
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnswerID")
    private Integer answerID;

    @Column(name = "QuestionID")
    private Integer questionID;

    @Column(name = "AnswerText", columnDefinition = "TEXT")
    private String answerText;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;

    @Column(name = "AnswerNumber")
    private Integer answerNumber;

    @ManyToOne
    @JoinColumn(name = "QuestionID", insertable = false, updatable = false)
    private Question question;

    @PrePersist
    @PreUpdate
    private void validateAnswerNumber() {
        if (answerNumber != null && (answerNumber < 1 || answerNumber > 4)) {
            throw new IllegalArgumentException("AnswerNumber must be between 1 and 4");
        }
    }
}



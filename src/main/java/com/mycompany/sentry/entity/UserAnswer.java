package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserAnswer")
@Data
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResultID")
    private Integer resultID;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "QuestionID")
    private Integer questionID;

    @Column(name = "UserAnswer", columnDefinition = "TEXT")
    private String userAnswer;

    @Column(name = "TakenAt")
    private LocalDateTime takenAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "QuestionID", insertable = false, updatable = false)
    private Question question;
}



package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Progress")
@Data
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgressID")
    private Integer progressID;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "CourseID")
    private Integer courseID;

    @Column(name = "LessonID")
    private Integer lessonID;

    @Column(name = "CompletionPercent")
    private Integer completionPercent;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CourseID", insertable = false, updatable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "LessonID", insertable = false, updatable = false)
    private Lesson lesson;

    @PrePersist
    @PreUpdate
    private void validateCompletionPercent() {
        if (completionPercent != null && (completionPercent < 0 || completionPercent > 100)) {
            throw new IllegalArgumentException("CompletionPercent must be between 0 and 100");
        }
    }
}





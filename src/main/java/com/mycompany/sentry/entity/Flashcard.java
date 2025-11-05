package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Flashcards")
@Data
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FlashcardID")
    private Integer flashcardID;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "Title", length = 100)
    private String title;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "IsPublic")
    private Boolean isPublic = false;

    @Column(name = "Description", length = 500)
    private String description;

    @Column(name = "CoverImage", length = 500)
    private String coverImage;

    @Column(name = "LessonID")
    private Integer lessonID;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "LessonID", insertable = false, updatable = false)
    private Lesson lesson;
}



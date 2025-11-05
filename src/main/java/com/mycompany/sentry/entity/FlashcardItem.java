package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "FlashcardItems")
@Data
public class FlashcardItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FlashcardItemID")
    private Integer flashcardItemID;

    @Column(name = "FlashcardID")
    private Integer flashcardID;

    @Column(name = "VocabID")
    private Integer vocabID;

    @Column(name = "Note", length = 255)
    private String note;

    @Column(name = "FrontContent", length = 500)
    private String frontContent;

    @Column(name = "BackContent", length = 500)
    private String backContent;

    @Column(name = "FrontImage", length = 500)
    private String frontImage;

    @Column(name = "BackImage", length = 500)
    private String backImage;

    @Column(name = "OrderIndex")
    private Integer orderIndex = 0;

    @ManyToOne
    @JoinColumn(name = "FlashcardID", insertable = false, updatable = false)
    private Flashcard flashcard;

    @ManyToOne
    @JoinColumn(name = "VocabID", insertable = false, updatable = false)
    private Vocabulary vocabulary;
}



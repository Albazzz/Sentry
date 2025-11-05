package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "CourseRatings")
@Data
public class CourseRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RatingID")
    private Integer ratingID;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "CourseID")
    private Integer courseID;

    @Column(name = "Rating")
    private Integer rating;

    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "RatedAt")
    private LocalDateTime ratedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CourseID", insertable = false, updatable = false)
    private Course course;

    @PrePersist
    @PreUpdate
    private void validateRating() {
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}





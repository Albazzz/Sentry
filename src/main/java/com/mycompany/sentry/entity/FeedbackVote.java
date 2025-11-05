package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "FeedbackVotes")
@Data
public class FeedbackVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VoteID")
    private Integer voteID;

    @Column(name = "FeedbackID")
    private Integer feedbackID;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "VoteType")
    private Integer voteType;

    @ManyToOne
    @JoinColumn(name = "FeedbackID", insertable = false, updatable = false)
    private Feedback feedback;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @PrePersist
    @PreUpdate
    private void validateVoteType() {
        if (voteType != null && voteType != 1 && voteType != -1) {
            throw new IllegalArgumentException("VoteType must be 1 or -1");
        }
    }
}




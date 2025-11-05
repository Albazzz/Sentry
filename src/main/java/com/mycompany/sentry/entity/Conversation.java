package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Conversations")
@Data
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConversationID")
    private Integer conversationID;

    @Column(name = "User1ID", nullable = false)
    private Integer user1ID;

    @Column(name = "User2ID", nullable = false)
    private Integer user2ID;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "User1ID", insertable = false, updatable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "User2ID", insertable = false, updatable = false)
    private User user2;
}




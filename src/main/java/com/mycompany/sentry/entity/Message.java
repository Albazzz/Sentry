package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageID")
    private Integer messageID;

    @Column(name = "ConversationID", nullable = false)
    private Integer conversationID;

    @Column(name = "SenderID", nullable = false)
    private Integer senderID;

    @Column(name = "Content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "Type", length = 50, nullable = false)
    private String type;

    @Column(name = "IsRead")
    private Boolean isRead = false;

    @Column(name = "IsRecall")
    private Boolean isRecall = false;

    @Column(name = "SentAt")
    private LocalDateTime sentAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ConversationID", insertable = false, updatable = false)
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "SenderID", insertable = false, updatable = false)
    private User sender;
}




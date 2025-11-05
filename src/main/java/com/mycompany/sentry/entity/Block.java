package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Blocks")
@Data
@IdClass(BlockId.class)
public class Block {
    @Id
    @Column(name = "BlockerID")
    private Integer blockerID;

    @Id
    @Column(name = "BlockedID")
    private Integer blockedID;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "BlockerID", insertable = false, updatable = false)
    private User blocker;

    @ManyToOne
    @JoinColumn(name = "BlockedID", insertable = false, updatable = false)
    private User blocked;
}





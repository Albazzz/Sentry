package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GoogleToken")
@Data
public class GoogleToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GGID")
    private Integer ggID;

    @Column(name = "UserID")
    private Integer userID;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;
}





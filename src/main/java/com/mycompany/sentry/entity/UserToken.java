package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserToken")
@Data
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccessTokenID")
    private Integer accessTokenID;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "RefreshID", length = 255)
    private String refreshID;

    @Column(name = "DeviceID", length = 255)
    private String deviceID;

    @Column(name = "IPAddress", length = 50)
    private String ipAddress;

    @Column(name = "ExpiresAt")
    private LocalDateTime expiresAt;

    @Column(name = "IsRevoked")
    private Boolean isRevoked = false;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;
}




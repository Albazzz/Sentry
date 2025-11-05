package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "OTP")
@Data
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer otpID;
    
    @Column(name = "Email", nullable = false)
    private String email;
    
    @Column(name = "Code", nullable = false, length = 6)
    private String code;
    
    @Column(name = "ExpiresAt", nullable = false)
    private LocalDateTime expiresAt;
    
    @Column(name = "IsUsed", nullable = false)
    private Boolean isUsed = false;
    
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}

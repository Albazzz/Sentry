package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Integer paymentID;

    @Column(name = "UserID", nullable = false)
    private Integer userID;

    @Column(name = "PlanID", nullable = false)
    private Integer planID;

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    @Column(name = "ResponseCode", length = 20)
    private String responseCode;

    @Column(name = "Status", length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "PlanID", insertable = false, updatable = false)
    private PremiumPlan premiumPlan;
}



package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserPremium")
@Data
@IdClass(UserPremiumId.class)
public class UserPremium {
    @Id
    @Column(name = "UserID")
    private Integer userID;

    @Id
    @Column(name = "PlanID")
    private Integer planID;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "PlanID", insertable = false, updatable = false)
    private PremiumPlan premiumPlan;
}


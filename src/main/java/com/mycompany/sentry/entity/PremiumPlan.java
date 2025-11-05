package com.mycompany.sentry.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "PremiumPlans")
@Data
public class PremiumPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanID")
    private Integer planID;

    @Column(name = "PlanName")
    private String planName;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "DurationInMonths")
    private Integer durationInMonths;

    @Column(name = "Description")
    private String description;
}



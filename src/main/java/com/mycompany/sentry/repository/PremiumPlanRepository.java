package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.PremiumPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumPlanRepository extends JpaRepository<PremiumPlan, Integer> {
}





package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.PremiumPlan;
import com.mycompany.sentry.repository.PremiumPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/premium-plans")
public class PremiumPlanController {
    @Autowired
    private PremiumPlanRepository premiumPlanRepository;

    @GetMapping
    public List<PremiumPlan> getAllPlans() {
        return premiumPlanRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PremiumPlan> getPlanById(@PathVariable Integer id) {
        Optional<PremiumPlan> plan = premiumPlanRepository.findById(id);
        return plan.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}




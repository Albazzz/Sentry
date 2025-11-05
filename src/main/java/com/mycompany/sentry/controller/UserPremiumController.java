package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.UserPremium;
import com.mycompany.sentry.repository.UserPremiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-premium")
public class UserPremiumController {
    @Autowired
    private UserPremiumRepository userPremiumRepository;

    @GetMapping
    public List<UserPremium> getAllUserPremium() {
        return userPremiumRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<UserPremium> getUserPremiumByUser(@PathVariable Integer userId) {
        return userPremiumRepository.findByUserID(userId);
    }

    @GetMapping("/user/{userId}/plan/{planId}")
    public ResponseEntity<UserPremium> getUserPremiumByUserAndPlan(@PathVariable Integer userId, 
                                                                   @PathVariable Integer planId) {
        Optional<UserPremium> userPremium = userPremiumRepository.findByUserIDAndPlanID(userId, planId);
        return userPremium.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserPremium createUserPremium(@RequestBody UserPremium userPremium) {
        return userPremiumRepository.save(userPremium);
    }
}


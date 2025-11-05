package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findByEmailAndCodeAndIsUsedFalseAndExpiresAtAfter(String email, String code, LocalDateTime now);
    
    Optional<OTP> findTopByEmailAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(String email, LocalDateTime now);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE OTP o SET o.isUsed = true WHERE o.email = ?1 AND o.isUsed = false")
    void markAllAsUsedByEmail(String email);
}


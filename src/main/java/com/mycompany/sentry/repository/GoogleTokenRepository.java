package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.GoogleToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleTokenRepository extends JpaRepository<GoogleToken, Integer> {
    Optional<GoogleToken> findByUserID(Integer userID);
}





package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
    Optional<UserToken> findByUserID(Integer userID);
}





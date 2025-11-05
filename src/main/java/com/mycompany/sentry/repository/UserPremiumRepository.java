package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.UserPremium;
import com.mycompany.sentry.entity.UserPremiumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPremiumRepository extends JpaRepository<UserPremium, UserPremiumId> {
    List<UserPremium> findByUserID(Integer userID);
    Optional<UserPremium> findByUserIDAndPlanID(Integer userID, Integer planID);
}





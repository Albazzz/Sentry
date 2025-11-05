package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    List<UserAnswer> findByUserID(Integer userID);
}





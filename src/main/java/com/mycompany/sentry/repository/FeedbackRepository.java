package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByCourseID(Integer courseID);
    List<Feedback> findByUserID(Integer userID);
}





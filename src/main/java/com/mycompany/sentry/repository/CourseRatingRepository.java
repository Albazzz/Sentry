package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRatingRepository extends JpaRepository<CourseRating, Integer> {
    List<CourseRating> findByCourseID(Integer courseID);
    List<CourseRating> findByUserID(Integer userID);
    Optional<CourseRating> findByCourseIDAndUserID(Integer courseID, Integer userID);
}




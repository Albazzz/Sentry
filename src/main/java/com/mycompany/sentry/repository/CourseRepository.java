package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByIsHiddenFalse();
    List<Course> findByIsSuggestedTrue();
    List<Course> findByCreatedBy(Integer createdBy);
}




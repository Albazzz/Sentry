package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByCreatedBy(Integer createdBy);
    List<Course> findByIsHidden(Boolean isHidden);
    List<Course> findByIsSuggested(Boolean isSuggested);
    List<Course> findByIsHiddenAndIsSuggested(Boolean isHidden, Boolean isSuggested);

    @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Course> searchByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(concat('%', :keyword, '%')) AND c.isHidden = false")
    List<Course> searchVisibleByTitleContainingIgnoreCase(String keyword);
}
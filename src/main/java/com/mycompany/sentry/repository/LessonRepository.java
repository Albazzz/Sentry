package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByCourseID(Integer courseID);
    List<Lesson> findByCourseIDOrderByLessonIDAsc(Integer courseID);
}





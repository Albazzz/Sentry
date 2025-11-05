package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    List<Progress> findByUserID(Integer userID);
    List<Progress> findByLessonID(Integer lessonID);
    Optional<Progress> findByUserIDAndLessonID(Integer userID, Integer lessonID);
}





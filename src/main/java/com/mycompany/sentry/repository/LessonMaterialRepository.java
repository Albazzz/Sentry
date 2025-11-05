package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.LessonMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonMaterialRepository extends JpaRepository<LessonMaterial, Integer> {
    List<LessonMaterial> findByLessonID(Integer lessonID);
}





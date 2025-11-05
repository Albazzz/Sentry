package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {
    List<Vocabulary> findByLessonID(Integer lessonID);
}




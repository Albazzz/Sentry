package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.LessonVocabulary;
import com.mycompany.sentry.entity.LessonVocabularyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonVocabularyRepository extends JpaRepository<LessonVocabulary, LessonVocabularyId> {
    List<LessonVocabulary> findByLessonID(Integer lessonID);
    List<LessonVocabulary> findByVocabID(Integer vocabID);
}





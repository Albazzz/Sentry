package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Kanji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KanjiRepository extends JpaRepository<Kanji, Integer> {
    List<Kanji> findByLessonID(Integer lessonID);
}





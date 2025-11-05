package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.FlashcardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardItemRepository extends JpaRepository<FlashcardItem, Integer> {
    List<FlashcardItem> findByFlashcardID(Integer flashcardID);
}




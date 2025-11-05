package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.Flashcard;
import com.mycompany.sentry.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {
    @Autowired
    private FlashcardRepository flashcardRepository;

    @GetMapping
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Flashcard> getFlashcardsByUser(@PathVariable Integer userId) {
        return flashcardRepository.findByUserID(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> getFlashcardById(@PathVariable Integer id) {
        Optional<Flashcard> flashcard = flashcardRepository.findById(id);
        return flashcard.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Flashcard createFlashcard(@RequestBody Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }
}





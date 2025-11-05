package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.Progress;
import com.mycompany.sentry.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {
    @Autowired
    private ProgressRepository progressRepository;

    @GetMapping
    public List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Progress> getProgressByUser(@PathVariable Integer userId) {
        return progressRepository.findByUserID(userId);
    }

    @GetMapping("/lesson/{lessonId}")
    public List<Progress> getProgressByLesson(@PathVariable Integer lessonId) {
        return progressRepository.findByLessonID(lessonId);
    }

    @GetMapping("/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<Progress> getProgressByUserAndLesson(@PathVariable Integer userId, 
                                                                 @PathVariable Integer lessonId) {
        Optional<Progress> progress = progressRepository.findByUserIDAndLessonID(userId, lessonId);
        return progress.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Progress createOrUpdateProgress(@RequestBody Progress progress) {
        return progressRepository.save(progress);
    }
}




package com.mycompany.sentry.controller;

import com.mycompany.sentry.entity.Conversation;
import com.mycompany.sentry.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    @Autowired
    private ConversationRepository conversationRepository;

    @GetMapping
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Conversation> getConversationsByUser(@PathVariable Integer userId) {
        return conversationRepository.findByUser1IDOrUser2ID(userId, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable Integer id) {
        Optional<Conversation> conversation = conversationRepository.findById(id);
        return conversation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Conversation createConversation(@RequestBody Conversation conversation) {
        return conversationRepository.save(conversation);
    }
}





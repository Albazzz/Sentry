package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> findByUser1ID(Integer user1ID);
    List<Conversation> findByUser2ID(Integer user2ID);
    List<Conversation> findByUser1IDOrUser2ID(Integer user1ID, Integer user2ID);
}





package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByConversationID(Integer conversationID);
    List<Message> findBySenderID(Integer senderID);
    List<Message> findByConversationIDOrderBySentAtAsc(Integer conversationID);
}




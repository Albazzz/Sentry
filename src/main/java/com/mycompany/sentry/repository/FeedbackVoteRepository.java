package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.FeedbackVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackVoteRepository extends JpaRepository<FeedbackVote, Integer> {
    List<FeedbackVote> findByFeedbackID(Integer feedbackID);
    List<FeedbackVote> findByUserID(Integer userID);
    Optional<FeedbackVote> findByFeedbackIDAndUserID(Integer feedbackID, Integer userID);
}




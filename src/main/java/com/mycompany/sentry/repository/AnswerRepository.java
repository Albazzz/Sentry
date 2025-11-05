package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionID(Integer questionID);
}





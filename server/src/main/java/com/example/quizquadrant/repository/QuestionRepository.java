package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;
import com.example.quizquadrant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q WHERE q.createdBy = :user")
    List<Question> findByCreatedBy(User user);

}

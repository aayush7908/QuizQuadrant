package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q WHERE q.createdBy = :user")
    Page<Question> findByCreatedBy(User user, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.subtopic in :subtopics AND q.isPublic = true")
    Page<Question> findBySubtopics(List<Subtopic> subtopics, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Question q WHERE q.subtopic = :subtopic AND q.isPublic = true")
    Integer countBySubtopic(Subtopic subtopic);

    @Query("SELECT COUNT(*) FROM Question q WHERE q.subtopic in :subtopics AND q.isPublic = true")
    Integer countBySubtopics(List<Subtopic> subtopics);

    @Query("SELECT COUNT(*) FROM Question q WHERE q.createdBy = :user")
    Integer countByCreatedBy(User user);

}

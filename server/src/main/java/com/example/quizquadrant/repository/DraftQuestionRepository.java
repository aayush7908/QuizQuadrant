package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.DraftExam;
import com.example.quizquadrant.model.DraftQuestion;
import com.example.quizquadrant.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DraftQuestionRepository extends JpaRepository<DraftQuestion, UUID> {

    @Query("SELECT d FROM DraftQuestion d WHERE d.createdBy = :user")
    Page<DraftQuestion> findByCreatedBy(User user, Pageable pageable);

    @Query("SELECT COUNT(*) FROM DraftQuestion d WHERE d.createdBy = :user")
    Integer countByCreatedBy(User user);

}

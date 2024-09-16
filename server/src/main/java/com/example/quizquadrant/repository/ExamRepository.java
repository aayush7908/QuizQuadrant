package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ExamRepository extends JpaRepository<Exam, UUID> {

    @Query("SELECT e FROM Exam e WHERE e.createdBy = :user")
    Page<Exam> findByCreatedBy(User user, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Exam e WHERE e.createdBy = :user")
    Integer countByCreatedBy(User user);

}

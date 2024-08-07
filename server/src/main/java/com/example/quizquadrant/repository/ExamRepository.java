package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

import java.util.Optional;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Exam e SET e.isResultGenerated = true WHERE e.id = :examId")
    public void markResultGenerated(Long examId);
}

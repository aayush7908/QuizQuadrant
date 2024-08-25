package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.ExamCandidate;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.key.ExamCandidateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ExamCandidateRepository extends JpaRepository<ExamCandidate, ExamCandidateKey> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM ExamCandidate e WHERE e.exam = :exam AND e.user = :user")
    Boolean existsByExamAndUser(Exam exam, User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM ExamCandidate e WHERE e.exam = :exam AND e.user = :user")
    void deleteByExamAndUser(Exam exam, User user);

}

package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.ExamResponse;
import com.example.quizquadrant.model.key.ExamResponseKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExamResponseRepository extends JpaRepository<ExamResponse, ExamResponseKey> {
}

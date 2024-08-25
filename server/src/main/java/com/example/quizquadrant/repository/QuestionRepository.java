package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}

package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;
import com.example.quizquadrant.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SolutionRepository extends JpaRepository<Solution, UUID> {

    @Query("SELECT s from Solution  s WHERE s.question = :question")
    Optional<Solution> findByQuestion(Question question);

}

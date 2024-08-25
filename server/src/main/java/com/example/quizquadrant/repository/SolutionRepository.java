package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Solution;
import com.example.quizquadrant.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SolutionRepository extends JpaRepository<Solution, UUID> {
}

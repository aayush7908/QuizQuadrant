package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.PrivateQuestion;
import com.example.quizquadrant.model.PrivateSolution;
import com.example.quizquadrant.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
}

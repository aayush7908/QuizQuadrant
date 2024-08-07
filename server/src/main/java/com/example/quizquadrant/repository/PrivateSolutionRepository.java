package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.PrivateSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PrivateSolutionRepository extends JpaRepository<PrivateSolution, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM PrivateSolution ps where ps in :privateSolution")
    public void deletePrivateSolution(PrivateSolution privateSolution);
}

package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.SubtopicKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtopicRepository extends JpaRepository<Subtopic, SubtopicKey> {

}
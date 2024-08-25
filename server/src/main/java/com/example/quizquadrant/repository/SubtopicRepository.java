package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SubtopicRepository extends JpaRepository<Subtopic, UUID> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Subtopic s WHERE s.name = :name AND s.subject = :subject")
    Boolean existsByNameAndSubject(String name, Subject subject);

}

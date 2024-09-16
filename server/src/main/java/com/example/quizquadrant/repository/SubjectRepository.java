package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Subject s WHERE s.name = :name")
    Boolean existsByName(String name);

    @Query(value = "SELECT s FROM Subject s WHERE s.id = :id")
    Optional<Subject> findOnlySubjectById(UUID id);

}

package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OptionRepository extends JpaRepository<Option, UUID> {

    @Query("SELECT o FROM Option o WHERE o.question = :question")
    Optional<List<Option>> findByQuestion(Question question);

}

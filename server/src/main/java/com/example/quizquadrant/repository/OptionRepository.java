package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OptionRepository extends JpaRepository<Option, UUID> {
}

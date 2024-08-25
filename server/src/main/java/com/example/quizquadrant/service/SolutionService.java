package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.dto.SolutionDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SolutionService {

    //    helper methods
    void create(
            SolutionDto solutionDto,
            Question question
    ) throws Exception;

    void update(
            SolutionDto solutionDto
    ) throws Exception;

    Solution getSolutionById(
            UUID id
    ) throws Exception;

}

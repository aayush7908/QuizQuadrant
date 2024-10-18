package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.question.SolutionRequestDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;

import java.util.UUID;

public interface SolutionService {

    //    controller service methods
    Solution create(
            SolutionRequestDto solutionRequestDto,
            Question question
    );

    Solution update(
            SolutionRequestDto solutionRequestDto,
            Question question
    ) throws Exception;

    //    repository access methods
    Solution createSolution(
            Solution solution
    );

    Solution updateSolution(
            Solution solution
    ) throws Exception;

    Solution getSolutionByQuestion(
            Question question
    ) throws Exception;

    Solution getSolutionById(
            UUID id
    ) throws Exception;

}

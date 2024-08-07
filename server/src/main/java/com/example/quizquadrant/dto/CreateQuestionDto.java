package com.example.quizquadrant.dto;

import java.util.List;

public record CreateQuestionDto(
        String type,
        Long subjectId,
        Long subtopicId,
        Integer positiveMarks,
        Integer negativeMarks,
        String questionStatement,
        String questionImageURL,
        String optionAStatement,
        String optionAImageURL,
        String optionBStatement,
        String optionBImageURL,
        String optionCStatement,
        String optionCImageURL,
        String optionDStatement,
        String optionDImageURL,
        String solutionStatement,
        String solutionImageURL,
        List<String> correctAnswer
) {
}

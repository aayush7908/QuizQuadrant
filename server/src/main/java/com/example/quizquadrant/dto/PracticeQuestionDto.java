package com.example.quizquadrant.dto;

import java.util.List;

public record PracticeQuestionDto(

        Long id,
        Integer positiveMarks,
        Integer negativeMarks,
        String statement,
        String type,
        String subtopic,
        String subject,
        PracticeSolutionDto solution,
        List<PracticeOptionDto> options,
        String imageURL
) {
}

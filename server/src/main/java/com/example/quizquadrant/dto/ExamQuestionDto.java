package com.example.quizquadrant.dto;

import com.example.quizquadrant.model.ExamResponses;

import java.util.List;

public record ExamQuestionDto(
        Long id,
        String statement,
        String type,
        Boolean hasImage,
        Integer positiveMarks,
        Integer negativeMarks,
        List<ExamOptionDto> options,
        String imageURL
) {
}

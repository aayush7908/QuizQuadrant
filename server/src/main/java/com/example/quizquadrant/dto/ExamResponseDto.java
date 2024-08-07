package com.example.quizquadrant.dto;

public record ExamResponseDto(
        Long questionId,
        Boolean optionAMarked,
        Boolean optionBMarked,
        Boolean optionCMarked,
        Boolean optionDMarked
) {
}

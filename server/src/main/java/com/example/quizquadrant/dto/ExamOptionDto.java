package com.example.quizquadrant.dto;

public record ExamOptionDto(
        Long id,
        String statement,
        Boolean hasImage,
        String imageURL,
        Boolean isMarked
) {
}

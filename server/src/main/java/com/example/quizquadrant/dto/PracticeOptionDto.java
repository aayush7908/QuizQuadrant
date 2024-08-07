package com.example.quizquadrant.dto;

public record PracticeOptionDto(
        Long id,
        String statement,
        Boolean hasImage,
        Boolean isCorrect,
        String imageURL
) {
}

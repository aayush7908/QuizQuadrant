package com.example.quizquadrant.dto;

public record PracticeSolutionDto(
        Long id,
        String statement,
        Boolean hasImage,
        String imageURL
) {
}

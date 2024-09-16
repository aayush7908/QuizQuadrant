package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record SolutionRequestDto(
        String statement,
        String imageUrl
) {
}

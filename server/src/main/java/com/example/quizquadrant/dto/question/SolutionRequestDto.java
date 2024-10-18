package com.example.quizquadrant.dto.question;

import lombok.Builder;

@Builder
public record SolutionRequestDto(
        String statement,
        String imageUrl
) {
}

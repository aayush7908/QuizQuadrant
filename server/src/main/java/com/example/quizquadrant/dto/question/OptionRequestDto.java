package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record OptionRequestDto(
        String statement,
        String imageUrl,
        Boolean isCorrect
) {
}

package com.example.quizquadrant.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OptionRequestDto(
        String statement,
        String imageUrl,
        Boolean isCorrect
) {
}

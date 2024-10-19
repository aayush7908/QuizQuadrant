package com.example.quizquadrant.dto.question;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OptionDto(
        UUID id,
        String statement,
        String imageUrl,
        Boolean isCorrect
) {
}

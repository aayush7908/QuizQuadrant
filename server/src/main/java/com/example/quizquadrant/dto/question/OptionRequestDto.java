package com.example.quizquadrant.dto.question;

import lombok.Builder;

@Builder
public record OptionRequestDto(
        String statement,
        String imageUrl,
        Boolean isCorrect
) {
}

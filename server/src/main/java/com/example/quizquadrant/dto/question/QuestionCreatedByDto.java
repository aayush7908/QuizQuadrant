package com.example.quizquadrant.dto.question;

import lombok.Builder;

import java.util.UUID;

@Builder
public record QuestionCreatedByDto(
        UUID id
) {
}

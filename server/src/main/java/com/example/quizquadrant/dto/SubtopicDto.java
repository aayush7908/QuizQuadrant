package com.example.quizquadrant.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubtopicDto(
        UUID id,
        String name,
        SubjectDto subject,
        Integer totalQuestions
) {
}
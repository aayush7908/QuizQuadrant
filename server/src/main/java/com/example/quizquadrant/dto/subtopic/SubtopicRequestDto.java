package com.example.quizquadrant.dto.subtopic;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubtopicRequestDto(
        String name,
        UUID subjectId
) {
}

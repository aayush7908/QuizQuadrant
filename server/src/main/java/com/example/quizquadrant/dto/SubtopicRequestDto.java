package com.example.quizquadrant.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubtopicRequestDto(
        String name,
        UUID subjectId
) {
}
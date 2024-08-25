package com.example.quizquadrant.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubjectDto(
        UUID id,
        String name
) {
}

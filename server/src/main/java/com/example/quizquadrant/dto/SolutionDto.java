package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SolutionDto(
        UUID id,
        String statement,
        String imageUrl
) {
}
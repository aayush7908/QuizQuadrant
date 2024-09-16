package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record IdResponseDto(
        UUID id
) {
}

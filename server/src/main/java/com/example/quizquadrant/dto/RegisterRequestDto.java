package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RegisterRequestDto(
        String email,
        String password,
        String firstName,
        String lastName,
        String profileImageUrl,
        String role
) {
}

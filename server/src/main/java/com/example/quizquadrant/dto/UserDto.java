package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String profileImageUrl,
        LocalDateTime accountCreatedOn,
        String role,
        Boolean isEmailVerified,
        Integer totalQuestions,
        Integer totalExams,
        Integer totalDraftExams,
        Integer totalDraftQuestions
) {
}

package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserProfileResponseDto(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String profileImageUrl,
        LocalDateTime accountCreatedOn,
        String role,
        Boolean isEmailVerified,
        Integer totalUsers
) {
}

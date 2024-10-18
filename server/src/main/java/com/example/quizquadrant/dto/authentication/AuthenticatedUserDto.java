package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthenticatedUserDto(
        UUID id,
        String email,
        String profileImageUrl,
        String role,
        Boolean isEmailVerified
) {
}

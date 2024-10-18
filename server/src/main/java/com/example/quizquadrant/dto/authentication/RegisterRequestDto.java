package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

@Builder
public record RegisterRequestDto(
        String email,
        String password,
        String firstName,
        String lastName,
        String role
) {
}

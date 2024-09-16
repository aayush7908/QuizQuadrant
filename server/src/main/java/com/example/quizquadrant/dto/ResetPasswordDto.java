package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record ResetPasswordDto(
        String email,
        String password,
        String token
) {
}

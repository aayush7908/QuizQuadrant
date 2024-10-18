package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record ResetPasswordRequestDto(
        String email,
        String password,
        String token
) {
}

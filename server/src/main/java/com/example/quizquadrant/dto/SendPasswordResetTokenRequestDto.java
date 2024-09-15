package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record SendPasswordResetTokenRequestDto(
        String email,
        String otp
) {
}

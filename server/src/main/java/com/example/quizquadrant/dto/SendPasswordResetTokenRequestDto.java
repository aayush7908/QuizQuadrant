package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record VerifyEmailRequestDto(
        String email,
        String otp
) {
}

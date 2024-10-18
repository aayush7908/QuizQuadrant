package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record VerifyEmailOtpRequestDto(
        String otp
) {
}

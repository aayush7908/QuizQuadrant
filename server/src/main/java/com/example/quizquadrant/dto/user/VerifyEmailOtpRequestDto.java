package com.example.quizquadrant.dto.user;

import lombok.Builder;

@Builder
public record VerifyEmailOtpRequestDto(
        String otp
) {
}

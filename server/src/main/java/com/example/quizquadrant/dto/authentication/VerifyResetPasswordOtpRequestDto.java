package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

@Builder
public record VerifyOtpRequestDto(
        String email,
        String otp
) {
}

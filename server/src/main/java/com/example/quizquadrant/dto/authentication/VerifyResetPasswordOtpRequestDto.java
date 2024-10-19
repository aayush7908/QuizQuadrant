package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

@Builder
public record VerifyResetPasswordOtpRequestDto(
        String email,
        String otp
) {
}

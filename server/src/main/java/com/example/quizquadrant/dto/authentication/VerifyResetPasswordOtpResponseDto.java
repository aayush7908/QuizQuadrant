package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

@Builder
public record VerifyResetPasswordOtpResponseDto(
        String token
) {
}

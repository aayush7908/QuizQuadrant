package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

@Builder
public record SendPasswordResetOtpRequestDto(
        String email
) {
}

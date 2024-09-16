package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record OtpRequestDto(
        String email
) {
}

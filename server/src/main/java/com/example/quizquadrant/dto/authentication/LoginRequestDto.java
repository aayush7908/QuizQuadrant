package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record LoginRequestDto(
        String email,
        String password
) {
}

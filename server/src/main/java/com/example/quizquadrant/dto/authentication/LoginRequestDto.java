package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

@Builder
public record LoginRequestDto(
        String email,
        String password
) {
}

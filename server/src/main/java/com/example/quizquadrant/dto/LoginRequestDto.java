package com.example.quizquadrant.dto.request;

import lombok.Builder;

@Builder
public record LoginRequestDto(
        String email,
        String password
) {
}

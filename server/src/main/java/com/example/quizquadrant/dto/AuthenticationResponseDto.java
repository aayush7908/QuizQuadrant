package com.example.quizquadrant.dto;

public record AuthenticationResponseDto(
        String token,
        String type,
        String name,
        String email,
        Long userId
) {
}

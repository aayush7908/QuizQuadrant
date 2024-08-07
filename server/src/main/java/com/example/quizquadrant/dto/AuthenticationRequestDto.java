package com.example.quizquadrant.dto;

public record AuthenticationRequestDto(
        String email,
        String password
) {
}

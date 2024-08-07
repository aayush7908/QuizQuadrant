package com.example.quizquadrant.dto;

public record RegisterRequestDto(
        String type,
        String name,
        String email,
        String password
) {
}

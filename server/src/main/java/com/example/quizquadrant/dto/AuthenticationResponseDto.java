package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(
        String token,
        UserDto user
) {
}

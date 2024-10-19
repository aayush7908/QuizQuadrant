package com.example.quizquadrant.dto.authentication;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(
        String token,
        AuthenticatedUserDto user
) {
}

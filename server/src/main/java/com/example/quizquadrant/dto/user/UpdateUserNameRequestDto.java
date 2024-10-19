package com.example.quizquadrant.dto.user;

import lombok.Builder;

@Builder
public record UpdateUserNameRequestDto(
        String firstName,
        String lastName
) {
}

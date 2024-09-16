package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record UpdateUserNameRequestDto(
        String firstName,
        String lastName
) {
}

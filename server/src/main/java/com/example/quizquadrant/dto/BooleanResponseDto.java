package com.example.quizquadrant.dto;

import lombok.Builder;

@Builder
public record BooleanResponseDto(
        Boolean success
) {
}

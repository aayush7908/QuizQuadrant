package com.example.quizquadrant.dto.subject;

import lombok.Builder;

@Builder
public record SubjectRequestDto(
        String name
) {
}

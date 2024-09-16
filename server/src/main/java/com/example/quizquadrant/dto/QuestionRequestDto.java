package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record QuestionRequestDto(
        String type,
        Boolean isPublic,
        String statement,
        String imageUrl,
        SubtopicDto subtopic,
        List<OptionRequestDto> options,
        SolutionRequestDto solution
) {
}

package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record QuestionDto(
        UUID id,
        Integer positiveMarks,
        Integer negativeMarks,
        String type,
        Boolean isPublic,
        String statement,
        String imageUrl,
        LocalDateTime lastModifiedOn,
        SubtopicDto subtopic,
        List<OptionDto> options,
        SolutionDto solution,
        UserDto createdBy
) {
}

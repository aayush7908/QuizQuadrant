package com.example.quizquadrant.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ExamDto(
        UUID id,
        String title,
        LocalDateTime startDateTime,
        Integer durationInMinutes,
        Boolean isResultGenerated,
        Integer totalMarks,
        LocalDateTime lastModifiedOn,
        UserDto createdBy,
        List<QuestionDto> questions,
        List<UserDto> candidates
) {
}

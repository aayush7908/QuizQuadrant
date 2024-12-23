package com.example.quizquadrant.dto.question;

import com.example.quizquadrant.dto.subtopic.SubtopicDto;
import com.example.quizquadrant.dto.user.UserDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record QuestionDto(
        UUID id,
        String type,
        Boolean isPublic,
        String statement,
        String imageUrl,
        LocalDateTime lastModifiedOn,
        SubtopicDto subtopic,
        List<OptionDto> options,
        SolutionDto solution,
        QuestionCreatedByDto createdBy,
        Integer totalQuestions
) {
}

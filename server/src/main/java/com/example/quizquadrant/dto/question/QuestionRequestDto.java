package com.example.quizquadrant.dto.question;

import com.example.quizquadrant.dto.subtopic.SubtopicDto;
import lombok.Builder;

import java.util.List;

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

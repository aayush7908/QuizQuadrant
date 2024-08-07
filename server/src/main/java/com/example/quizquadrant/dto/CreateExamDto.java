package com.example.quizquadrant.dto;

import java.util.List;

public record CreateExamDto(
        String title,
        Integer duration,
        String startDate,
        String startTime,
        List<CreateQuestionDto> questionDtos,
        List<String> emailIds
) {
}

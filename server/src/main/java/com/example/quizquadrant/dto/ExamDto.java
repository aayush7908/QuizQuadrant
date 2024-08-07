package com.example.quizquadrant.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ExamDto(
        Long id,
        String title,
        Integer duration,
        String startDate,
        String startTime,
        String candidateName,
        String candidateEmail,
        List<Long> questionIds
) {
}

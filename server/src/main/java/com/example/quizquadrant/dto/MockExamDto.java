package com.example.quizquadrant.dto;

import java.util.List;

public record MockExamDto(
        List<Long> qids,
        Long totalMarks
) {
}
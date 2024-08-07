package com.example.quizquadrant.dto;

import java.util.List;

public record UserProfileDto(
        String name,
        String email,
        String type,
        List<UserProfileExamDto> examsCreated,
        List<UserProfileExamDto> pastExams,
        List<UserProfileExamDto> ongoingExams,
        List<UserProfileExamDto> futureExams
) {
}

package com.example.quizquadrant.dto.subject;

import com.example.quizquadrant.dto.subtopic.SubtopicDto;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record SubjectDto(
        UUID id,
        String name,
        List<SubtopicDto> subtopics
) {
}

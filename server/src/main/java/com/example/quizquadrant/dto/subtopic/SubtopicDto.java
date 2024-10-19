package com.example.quizquadrant.dto.subtopic;

import com.example.quizquadrant.dto.subject.SubjectDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SubtopicDto(
        UUID id,
        String name,
        SubjectDto subject
) {
}

package com.example.quizquadrant.dto;
import com.example.quizquadrant.model.Subtopic;

import java.util.List;

public record CreateMockTestDto(
    List <SubtopicDto> subtopicDtos
) {
}

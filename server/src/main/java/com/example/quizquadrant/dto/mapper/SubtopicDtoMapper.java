package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.dto.SubtopicDto;
import com.example.quizquadrant.model.Subtopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubtopicDtoMapper {

    public SubtopicDto toDto(
            Subtopic subtopic,
            UUID subjectId
    ) {
        return SubtopicDto
                .builder()
                .id(subtopic.getId())
                .name(subtopic.getName())
                .subject(
                        SubjectDto
                                .builder()
                                .id(subjectId)
                                .build()
                )
                .build();
    }

    public SubtopicDto toDto(
            Subtopic subtopic
    ) {
        return SubtopicDto
                .builder()
                .id(subtopic.getId())
                .name(subtopic.getName())
                .subject(null)
                .build();
    }

    public List<SubtopicDto> toDtos(
            List<Subtopic> subtopics
    ) {
        List<SubtopicDto> subtopicDtos = new ArrayList<>();
        for (Subtopic subtopic : subtopics) {
            subtopicDtos.add(toDto(subtopic));
        }
        return subtopicDtos;
    }

}

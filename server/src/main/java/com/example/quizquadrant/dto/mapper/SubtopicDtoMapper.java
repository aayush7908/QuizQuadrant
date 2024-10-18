package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.subject.SubjectDto;
import com.example.quizquadrant.dto.subtopic.SubtopicDto;
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
            boolean includeSubject
    ) {
        return SubtopicDto
                .builder()
                .id(subtopic.getId())
                .name(subtopic.getName())
                .subject(
                        includeSubject ?
                                SubjectDto
                                        .builder()
                                        .id(subtopic.getSubject().getId())
                                        .name(subtopic.getSubject().getName())
                                        .build() :
                                null
                )
                .build();
    }

    public List<SubtopicDto> toDtos(
            List<Subtopic> subtopics
    ) {
        List<SubtopicDto> subtopicDtos = new ArrayList<>();
        for (Subtopic subtopic : subtopics) {
            subtopicDtos.add(toDto(subtopic, false));
        }
        return subtopicDtos;
    }

}

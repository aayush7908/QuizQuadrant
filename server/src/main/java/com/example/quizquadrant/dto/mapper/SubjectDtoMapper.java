package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.dto.SubtopicDto;
import com.example.quizquadrant.model.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectDtoMapper {

    private final SubtopicDtoMapper subtopicDtoMapper;

    public SubjectDto toDto(
            Subject subject
    ) {
        List<SubtopicDto> subtopicDtos = subtopicDtoMapper.toDtos(subject.getSubtopics());
        return SubjectDto
                .builder()
                .id(subject.getId())
                .name(subject.getName())
                .subtopics(subtopicDtos)
                .build();
    }

    public List<SubjectDto> toDtos(
            List<Subject> subjects
    ) {
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectDtos.add(toDto(subject));
        }
        return subjectDtos;
    }

}

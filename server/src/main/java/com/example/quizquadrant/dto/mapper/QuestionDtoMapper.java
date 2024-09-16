package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDtoMapper {

    private final OptionDtoMapper optionDtoMapper;
    private final SolutionDtoMapper solutionDtoMapper;
    private final SubjectService subjectService;

    public QuestionDto toDto(
            Question question
    ) throws Exception {
//        fetch subject by id
        Subject subject = subjectService.getSubjectById(question.getSubtopic().getSubject().getId());

//        create question and return
        return toDto(question, subject);
    }

    public QuestionDto toDto(
            Question question,
            Subject subject
    ) throws Exception {
//        create subject dto
        SubjectDto subjectDto = SubjectDto
                .builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();

//        create subtopic dto
        SubtopicDto subtopicDto = SubtopicDto
                .builder()
                .id(question.getSubtopic().getId())
                .name(question.getSubtopic().getName())
                .subject(subjectDto)
                .build();

//        create option dtos
        List<OptionDto> optionDtos = new ArrayList<>();
        for (Option option : question.getOptions()) {
            optionDtos.add(
                    optionDtoMapper.toDto(option)
            );
        }

//        create solution dto
        SolutionDto solutionDto = solutionDtoMapper.toDto(question.getSolution());

//        create question dto and return
        return QuestionDto
                .builder()
                .id(question.getId())
                .type(question.getType().name())
                .isPublic(question.getIsPublic())
                .statement(question.getStatement())
                .imageUrl(question.getImageUrl())
                .lastModifiedOn(question.getLastModifiedOn())
                .subtopic(subtopicDto)
                .options(optionDtos)
                .solution(solutionDto)
                .build();
    }

}

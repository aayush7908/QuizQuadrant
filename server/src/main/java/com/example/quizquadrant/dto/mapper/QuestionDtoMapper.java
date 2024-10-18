package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.question.OptionDto;
import com.example.quizquadrant.dto.question.QuestionCreatedByDto;
import com.example.quizquadrant.dto.question.QuestionDto;
import com.example.quizquadrant.dto.question.SolutionDto;
import com.example.quizquadrant.dto.subject.SubjectDto;
import com.example.quizquadrant.dto.subtopic.SubtopicDto;
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


    public QuestionDto toDto(
            Question question
    ) throws Exception {
//        create subject dto
        SubjectDto subjectDto = SubjectDto
                .builder()
                .id(question.getSubtopic().getSubject().getId())
                .name(question.getSubtopic().getSubject().getName())
                .build();

//        create subtopic dto
        SubtopicDto subtopicDto = SubtopicDto
                .builder()
                .id(question.getSubtopic().getId())
                .name(question.getSubtopic().getName())
                .subject(subjectDto)
                .build();

//        create option dtos
        List<OptionDto> optionDtos = optionDtoMapper.toDtos(question.getOptions());

//        create solution dto
        SolutionDto solutionDto = solutionDtoMapper.toDto(question.getSolution());

//        create question created by dto
        QuestionCreatedByDto questionCreatedByDto = QuestionCreatedByDto
                .builder()
                .id(question.getCreatedBy().getId())
                .build();

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
                .createdBy(questionCreatedByDto)
                .totalQuestions(0)
                .build();
    }

    public List<QuestionDto> toDtos(
            List<Question> questions
    ) throws Exception {
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            questionDtos.add(
                    toDto(question)
            );
        }
        return questionDtos;
    }

}

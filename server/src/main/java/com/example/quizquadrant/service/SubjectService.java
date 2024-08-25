package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subject;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SubjectService {

    //    controller service methods
    ResponseEntity<BooleanResponseDto> create(
            SubjectDto subjectDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> update(
            SubjectDto subjectDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception;

    //    helper methods
    Subject getSubjectById(
            UUID id
    ) throws Exception;
}

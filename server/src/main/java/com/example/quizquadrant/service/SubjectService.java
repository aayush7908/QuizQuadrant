package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.SubjectRequestDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.model.Subject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    //    controller service methods
    ResponseEntity<BooleanResponseDto> create(
            SubjectRequestDto subjectRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> update(
            SubjectRequestDto subjectRequestDto,
            String id
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception;

    ResponseEntity<List<SubjectDto>> getAll() throws Exception;

    ResponseEntity<SubjectDto> getSubjectById(
            String id
    ) throws Exception;

    //    repository access methods
    Subject createSubject(Subject subject);

    Subject updateSubject(Subject subject);

    void deleteSubject(UUID id);

    List<Subject> getAllSubjects();

    Subject getSubjectById(
            UUID id
    ) throws Exception;

}

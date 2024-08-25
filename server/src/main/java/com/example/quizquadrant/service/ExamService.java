package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.ExamDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ExamService {

    //    controller service methods
    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> create(
            ExamDto examDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> updateDetails(
            ExamDto examDto
    ) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> addCandidates(
            ExamDto examDto
    ) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> removeCandidates(
            ExamDto examDto
    ) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> addQuestions(
            ExamDto examDto
    ) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> removeQuestions(
            ExamDto examDto
    ) throws Exception;

    //    helper methods
    Exam getExamById(
            UUID id
    ) throws Exception;

    void authorizeUserExam(
            User user,
            Exam exam
    ) throws Exception;

}

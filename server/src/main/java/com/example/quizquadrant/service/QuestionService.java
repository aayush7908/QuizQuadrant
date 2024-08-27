package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    //    controller service methods
    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> create(
            QuestionDto questionDto
    ) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> update(
            QuestionDto questionDto,
            String id
    ) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception;

    ResponseEntity<List<QuestionDto>> getMyQuestions(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception;

    ResponseEntity<QuestionDto> getQuestionById(
            String id
    ) throws Exception;

    ResponseEntity<List<QuestionDto>> getQuestionsBySubject(
            String id,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception;

    ResponseEntity<List<QuestionDto>> getQuestionsBySubtopic(
            String id,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception;

    //    helper methods
    Question getQuestionById(
            UUID id
    ) throws Exception;

    Question create(
            QuestionDto questionDto,
            Boolean isPublic,
            User user
    ) throws Exception;

    void authorizeUserQuestion(
            User user,
            Question question
    ) throws Exception;
}

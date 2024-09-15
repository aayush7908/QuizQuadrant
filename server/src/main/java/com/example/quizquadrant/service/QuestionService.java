package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    //    controller service methods
    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> create(
            QuestionRequestDto questionRequestDto,
            String draftId
    ) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    ResponseEntity<BooleanResponseDto> update(
            QuestionRequestDto questionRequestDto,
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

    //    repository access methods
    Question createQuestion(Question question);

    Question updateQuestion(Question question);

    void deleteQuestion(UUID id);

    int countQuestionsCreatedByUser(User user);
    int countQuestionsBySubject(Subject subject);
    int countQuestionsBySubtopic(Subtopic subtopic);

    Question getQuestionById(
            UUID id
    ) throws Exception;

    List<Question> getQuestionsByUser(
            User user,
            Integer pageNumber,
            Integer pageSize
    );


    //    helper methods
    Question create(
            QuestionRequestDto questionRequestDto,
            User user
    ) throws Exception;

    Question update(
            QuestionRequestDto questionRequestDto,
            Question question
    ) throws Exception;

    void authorizeUserQuestion(
            User user,
            Question question
    ) throws Exception;

}

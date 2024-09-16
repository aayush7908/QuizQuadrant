package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.IdResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.model.DraftQuestion;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DraftQuestionService {

    //    controller service methods
    ResponseEntity<IdResponseDto> create(
            QuestionDto questionDto
    ) throws Exception;

    ResponseEntity<IdResponseDto> update(
            QuestionDto questionDto,
            String id
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception;

    ResponseEntity<List<QuestionDto>> getMyDraftQuestions(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception;

    ResponseEntity<QuestionDto> getDraftQuestionById(
            String id
    ) throws Exception;


    //    repository access methods
    DraftQuestion createDraftQuestion(DraftQuestion draftQuestion);

    DraftQuestion updateDraftQuestion(DraftQuestion draftQuestion);

    void deleteDraftQuestion(UUID id);

    DraftQuestion getDraftQuestionById(
            UUID id
    ) throws Exception;


    //    helper methods
    void authorizeUserDraftQuestion(
            User user,
            DraftQuestion draftQuestion
    ) throws Exception;

}

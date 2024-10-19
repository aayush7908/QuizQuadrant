package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.SavedQuestion;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SavedQuestionService {

    //    repository access methods
    SavedQuestion createSavedQuestion(SavedQuestion savedQuestion);

    void deleteSavedQuestion(SavedQuestion savedQuestion);

    SavedQuestion getSavedQuestion(
            User user,
            Question question
    ) throws Exception;

    List<SavedQuestion> getSavedQuestionsByUser(
            User user,
            Integer pageNumber,
            Integer pageSize
    );

    Boolean checkSavedQuestionExists(
            User user,
            Question question
    );

    Integer countQuestionsSavedByUser(User user);

    //    helper methods
    SavedQuestion saveQuestion(
            User user,
            Question question
    ) throws Exception;

    void unsaveQuestion(
            User user,
            Question question
    ) throws Exception;

}

package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.ExamDto;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.ExamQuestion;
import com.example.quizquadrant.model.Question;
import org.springframework.http.ResponseEntity;

public interface ExamQuestionService {

    //    helper methods
    ExamQuestion create(
            Exam exam,
            Question question,
            Integer positiveMarks,
            Integer negativeMarks
    ) throws Exception;

    ExamQuestion createOrUpdate(
            Exam exam,
            Question question,
            Integer positiveMarks,
            Integer negativeMarks
    ) throws Exception;

    void delete(
            Exam exam,
            Question question
    ) throws Exception;
}

package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.service.ExamService;
import com.example.quizquadrant.dto.ExamQuestionDto;
import com.example.quizquadrant.model.ExamResponses;
import com.example.quizquadrant.model.PrivateQuestion;
import com.example.quizquadrant.service.ExamResponsesService;
import com.example.quizquadrant.service.PrivateQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/exam")
public class ExamController {

    private final PrivateQuestionService privateQuestionService;
    private final ExamService examService;
    private final ExamResponsesService examResponsesService;

    @Autowired
    public ExamController(PrivateQuestionService privateQuestionService, ExamService examService, ExamResponsesService examResponsesService) {
        this.privateQuestionService = privateQuestionService;
        this.examService = examService;
        this.examResponsesService = examResponsesService;
    }

    @GetMapping("/get-question-by-id")
    public ExamQuestionDto getPrivateQuestionById(
            @RequestParam("userId") Long userId,
            @RequestParam("questionId") Long privateQuestionId
    ) {
        return privateQuestionService.getPrivateQuestionById(userId, privateQuestionId);
    }

    @GetMapping("/get-exam-by-id")
    public ExamDto getExamById(
            @RequestParam("userId") Long userId,
            @RequestParam("examId") Long examId
    ) {
        return examService.getExamById(userId, examId);
    }

    @GetMapping("/calculate-result")
    public void calculateResult(
            @RequestParam("examId") Long examId
    ) {
        examService.calculateResult(examId);
    }

    @PostMapping("/create-exam")
    public Exam createExam(
            @RequestParam("userId") Long userId,
            @RequestBody CreateExamDto createExamDto
    ) {
        return examService.createExam(userId, createExamDto);
    }
      
    @PostMapping("/store-response")
    public ExamResponses StorePrivateQuestionResponse (@RequestParam("privateQuestionId") Long privateQuestionId, @RequestParam("userId") Long userId, @RequestBody PrivateQuestionResponsesDto privateQuestionResponsesDto) {
        return examResponsesService.storePrivateQuestionResponses(userId, privateQuestionId, privateQuestionResponsesDto);
    }

}

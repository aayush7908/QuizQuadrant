package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.CreateQuestionDto;
import com.example.quizquadrant.dto.PracticeQuestionDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@CrossOrigin
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/get-questions-by-subtopic")
    public List<PracticeQuestionDto> getQuestionsBySubtopic(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("subtopicId") Long subtopicId,
            @RequestParam("pageNumber") Integer setNumber
    ) {
        return questionService.getQuestionsBySubtopic(subjectId, subtopicId, setNumber);
    }

    @GetMapping("/get-questions-by-subject")
    public List<PracticeQuestionDto> getQuestionsBySubject(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("pageNumber") Integer setNumber
    ) {
        return questionService.getQuestionsBySubject(subjectId, setNumber);
    }

    @PostMapping("/create-question")
    public Question createQuestion(@RequestBody CreateQuestionDto createQuestionDto) {
        return questionService.createQuestion(createQuestionDto);
    }

    @GetMapping("/get-question-by-id")
    public PracticeQuestionDto getQuestion(@RequestParam("questionID") Long qid) {
        return questionService.getQuestionById(qid);
    }

}
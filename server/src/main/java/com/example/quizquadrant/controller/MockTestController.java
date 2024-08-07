package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.CreateMockTestDto;
import com.example.quizquadrant.dto.MockExamDto;
import com.example.quizquadrant.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/mock-test")
public class MockTestController {

    private final QuestionService questionService;

    @Autowired
    public MockTestController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/get-question-Ids")
    public MockExamDto getQuestionIds(@RequestBody CreateMockTestDto createMockTestDto, @RequestParam("total") Integer total) {
        return questionService.getQuestionIdsBySubtopics(createMockTestDto,total);
    }

//    @PostMapping("/get-total-question")
//    public Long getTotalQuestions(@RequestBody CreateMockTestDto createMockTestDto) {
//        return questionService.totalNumberOfQuestions(createMockTestDto);
//    }

}

package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<BooleanResponseDto> create(
            @RequestBody QuestionDto questionDto
    ) throws Exception {
        return questionService.create(questionDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BooleanResponseDto> update(
            @PathVariable String id,
            @RequestBody QuestionDto questionDto
    ) throws Exception {
        return questionService.update(questionDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return questionService.delete(id);
    }

    @GetMapping("/get/my")
    public ResponseEntity<List<QuestionDto>> getMyQuestions() throws Exception {
        return questionService.getMyQuestions();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionDto> getById(
            @PathVariable String id
    ) throws Exception {
        return questionService.getById(id);
    }

}

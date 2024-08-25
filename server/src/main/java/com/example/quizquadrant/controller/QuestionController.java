package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.service.QuestionService;
import com.example.quizquadrant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<BooleanResponseDto> update(
            @RequestBody QuestionDto questionDto
    ) throws Exception {
        return questionService.update(questionDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return questionService.delete(id);
    }

}

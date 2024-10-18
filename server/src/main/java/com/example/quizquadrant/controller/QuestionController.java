package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.IdResponseDto;
import com.example.quizquadrant.dto.question.QuestionDto;
import com.example.quizquadrant.dto.question.QuestionRequestDto;
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


    //    ROUTE: 1 => Create Question
    //    POST: "/api/question/create"
    @PostMapping("/create")
    public ResponseEntity<IdResponseDto> create(
            @RequestBody QuestionRequestDto questionRequestDto,
            @RequestParam("draftId") String draftId
    ) throws Exception {
        return questionService.create(questionRequestDto, draftId);
    }


    //    ROUTE: 2 => Update Question
    //    PUT: "/api/question/update/:id"
    @PutMapping("/update/{id}")
    public ResponseEntity<IdResponseDto> update(
            @PathVariable String id,
            @RequestBody QuestionRequestDto questionRequestDto
    ) throws Exception {
        return questionService.update(questionRequestDto, id);
    }


    //    ROUTE: 3 => Delete Question
    //    DELETE: "/api/question/delete/:id"
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return questionService.delete(id);
    }


    //    ROUTE: 4 => Save a Question
    //    POST: "/api/question/save/:id"
    @PostMapping("/save/{id}")
    public ResponseEntity<BooleanResponseDto> save(
            @PathVariable String id
    ) throws Exception {
        return questionService.save(id);
    }


    //    ROUTE: 5 => Unsave a Question
    //    POST: "/api/question/unsave/:id"
    @PostMapping("/unsave/{id}")
    public ResponseEntity<BooleanResponseDto> unsave(
            @PathVariable String id
    ) throws Exception {
        return questionService.unsave(id);
    }


    //    ROUTE: 6 => Get all questions created by the authenticated user (TEACHER)
    //    GET: "/api/question/get/my/created"
    @GetMapping("/get/my/created")
    public ResponseEntity<List<QuestionDto>> getMyCreatedQuestions(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return questionService.getMyCreatedQuestions(pageNumber, pageSize);
    }


    //    ROUTE: 7 => Get all questions saved by the authenticated user
    //    GET: "/api/question/get/my/saved"
    @GetMapping("/get/my/saved")
    public ResponseEntity<List<QuestionDto>> getMySavedQuestions(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return questionService.getMySavedQuestions(pageNumber, pageSize);
    }


    //    ROUTE: 8 => Get question by id
    //    GET: "/api/question/get/by-id/:id"
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<QuestionDto> getById(
            @PathVariable String id
    ) throws Exception {
        return questionService.getQuestionById(id);
    }


    //    ROUTE: 9 => Get questions by subject
    //    GET: "/api/question/get/by-subject/:id"
    @GetMapping("/get/by-subject/{id}")
    public ResponseEntity<List<QuestionDto>> getBySubject(
            @PathVariable String id,
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return questionService.getQuestionsBySubject(id, pageNumber, pageSize);
    }


    //    ROUTE: 10 => Get question by subtopic
    //    GET: "/api/question/get/by-subtopic/:id"
    @GetMapping("/get/by-subtopic/{id}")
    public ResponseEntity<List<QuestionDto>> getBySubtopic(
            @PathVariable String id,
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return questionService.getQuestionsBySubtopic(id, pageNumber, pageSize);
    }

}

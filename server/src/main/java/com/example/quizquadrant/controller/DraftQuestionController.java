package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.IdResponseDto;
import com.example.quizquadrant.dto.question.QuestionDto;
import com.example.quizquadrant.dto.question.QuestionRequestDto;
import com.example.quizquadrant.service.DraftQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/draft/question")
public class DraftQuestionController {

    private final DraftQuestionService draftQuestionService;


    //    ROUTE: 1 => Create DraftQuestion
    //    POST: "/api/draft/question/create"
    @PostMapping("/create")
    public ResponseEntity<IdResponseDto> create(
            @RequestBody QuestionRequestDto questionRequestDto
    ) throws Exception {
        return draftQuestionService.create(questionRequestDto);
    }


    //    ROUTE: 2 => Update a DraftQuestion
    //    PUT: "/api/draft/question/update/:id"
    @PutMapping("/update/{id}")
    public ResponseEntity<IdResponseDto> update(
            @PathVariable String id,
            @RequestBody QuestionRequestDto questionRequestDto
    ) throws Exception {
        return draftQuestionService.update(questionRequestDto, id);
    }


    //    ROUTE: 3 => Delete a DraftQuestion
    //    DELETE: "/api/draft/question/delete/:id"
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return draftQuestionService.delete(id);
    }


    //    ROUTE: 4 => Get all DraftQuestions created by the authenticated user
    //    GET: "/api/draft/question/get/my/created"
    @GetMapping("/get/my/created")
    public ResponseEntity<List<QuestionDto>> getMyDraftExams(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return draftQuestionService.getMyDraftQuestions(pageNumber, pageSize);
    }


    //    ROUTE: 5 => Get a DraftQuestion by ID
//    GET: "/api/draft/question/get/by-id/:id"
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<QuestionDto> getById(
            @PathVariable String id
    ) throws Exception {
        return draftQuestionService.getDraftQuestionById(id);
    }

}

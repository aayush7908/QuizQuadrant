package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.ExamDto;
import com.example.quizquadrant.dto.IdResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.service.DraftExamService;
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

    @PostMapping("/create")
    public ResponseEntity<IdResponseDto> create(
            @RequestBody QuestionDto questionDto
    ) throws Exception {
        return draftQuestionService.create(questionDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BooleanResponseDto> update(
            @PathVariable String id,
            @RequestBody QuestionDto questionDto
    ) throws Exception {
        return draftQuestionService.update(questionDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return draftQuestionService.delete(id);
    }

    @GetMapping("/get/my")
    public ResponseEntity<List<QuestionDto>> getMyDraftExams(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return draftQuestionService.getMyDraftQuestions(pageNumber, pageSize);
    }

    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<QuestionDto> getById(
            @PathVariable String id
    ) throws Exception {
        return draftQuestionService.getDraftQuestionById(id);
    }

}

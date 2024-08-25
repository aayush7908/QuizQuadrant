package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.ExamDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.service.ExamService;
import com.example.quizquadrant.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/exam")
public class ExamController {

    private final ExamService examService;

    @PostMapping("/create")
    public ResponseEntity<BooleanResponseDto> create(
            @RequestBody ExamDto examDto
    ) throws Exception {
        return examService.create(examDto);
    }

    @PatchMapping("/update/details")
    public ResponseEntity<BooleanResponseDto> updateDetails(
            @RequestBody ExamDto examDto
    ) throws Exception {
        return examService.updateDetails(examDto);
    }

    @PatchMapping("/update/add-candidates")
    public ResponseEntity<BooleanResponseDto> addCandidates(
            @RequestBody ExamDto examDto
    ) throws Exception {
        return examService.addCandidates(examDto);
    }

    @PatchMapping("/update/remove-candidates")
    public ResponseEntity<BooleanResponseDto> removeCandidates(
            @RequestBody ExamDto examDto
    ) throws Exception {
        return examService.removeCandidates(examDto);
    }

    @PatchMapping("/update/add-questions")
    public ResponseEntity<BooleanResponseDto> addQuestions(
            @RequestBody ExamDto examDto
    ) throws Exception {
        return examService.addQuestions(examDto);
    }

    @PatchMapping("/update/remove-questions")
    public ResponseEntity<BooleanResponseDto> removeQuestions(
            @RequestBody ExamDto examDto
    ) throws Exception {
        return examService.removeQuestions(examDto);
    }

}

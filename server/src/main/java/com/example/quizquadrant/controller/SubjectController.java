package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.service.QuestionService;
import com.example.quizquadrant.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<BooleanResponseDto> create(
            @RequestBody SubjectDto subjectDto
    ) throws Exception {
        return subjectService.create(subjectDto);
    }

    @PutMapping("/update")
    public ResponseEntity<BooleanResponseDto> update(
            @RequestBody SubjectDto subjectDto
    ) throws Exception {
        return subjectService.update(subjectDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return subjectService.delete(id);
    }

}

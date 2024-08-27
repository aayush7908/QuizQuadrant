package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.service.QuestionService;
import com.example.quizquadrant.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<BooleanResponseDto> update(
            @RequestBody SubjectDto subjectDto,
            @PathVariable String id
    ) throws Exception {
        return subjectService.update(subjectDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return subjectService.delete(id);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<SubjectDto>> getAll() throws Exception {
        return subjectService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SubjectDto> getById(
            @PathVariable String id
    ) throws Exception {
        return subjectService.getById(id);
    }

}

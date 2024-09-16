package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.SubjectRequestDto;
import com.example.quizquadrant.dto.SubjectDto;
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
            @RequestBody SubjectRequestDto subjectRequestDto
            ) throws Exception {
        return subjectService.create(subjectRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BooleanResponseDto> update(
            @RequestBody SubjectRequestDto subjectRequestDto,
            @PathVariable String id
    ) throws Exception {
        return subjectService.update(subjectRequestDto, id);
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
        return subjectService.getSubjectById(id);
    }

}

package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.subject.SubjectDto;
import com.example.quizquadrant.dto.subject.SubjectRequestDto;
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


    //    ROUTE: 1 => Create Subject
    //    POST: "/api/subject/create"
    @PostMapping("/create")
    public ResponseEntity<BooleanResponseDto> create(
            @RequestBody SubjectRequestDto subjectRequestDto
    ) throws Exception {
        return subjectService.create(subjectRequestDto);
    }


    //    ROUTE: 2 => Update Subject
    //    PUT: "/api/subject/update/:id"
    @PutMapping("/update/{id}")
    public ResponseEntity<BooleanResponseDto> update(
            @RequestBody SubjectRequestDto subjectRequestDto,
            @PathVariable String id
    ) throws Exception {
        return subjectService.update(subjectRequestDto, id);
    }


    //    ROUTE: 3 => Delete Subject
    //    DELETE: "/api/subject/delete/:id"
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return subjectService.delete(id);
    }


    //    ROUTE: 4 => Get all subjects
    //    GET: "/api/subject/get/all"
    @GetMapping("/get/all")
    public ResponseEntity<List<SubjectDto>> getAll() throws Exception {
        return subjectService.getAll();
    }


    //    ROUTE: 5 => Get subject by id
    //    GET: "/api/subject/get/by-id/{id}"
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<SubjectDto> getById(
            @PathVariable String id
    ) throws Exception {
        return subjectService.getSubjectById(id);
    }

}

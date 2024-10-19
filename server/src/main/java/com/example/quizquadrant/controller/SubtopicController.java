package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.subtopic.SubtopicDto;
import com.example.quizquadrant.dto.subtopic.SubtopicRequestDto;
import com.example.quizquadrant.service.SubtopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/subtopic")
public class SubtopicController {

    private final SubtopicService subtopicService;


    //    ROUTE: 1 => Create Subtopic
    //    POST: "/api/subtopic/create"
    @PostMapping("/create")
    public ResponseEntity<BooleanResponseDto> create(
            @RequestBody SubtopicRequestDto subtopicRequestDto
    ) throws Exception {
        return subtopicService.create(subtopicRequestDto);
    }


    //    ROUTE: 2 => Update Subtopic
    //    PUT: "/api/subtopic/update/:id"
    @PutMapping("/update/{id}")
    public ResponseEntity<BooleanResponseDto> update(
            @RequestBody SubtopicRequestDto subtopicRequestDto,
            @PathVariable String id
    ) throws Exception {
        return subtopicService.update(subtopicRequestDto, id);
    }


    //    ROUTE: 3 => Delete Subtopic
    //    DELETE: "/api/subtopic/delete/:id"
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return subtopicService.delete(id);
    }


    //    ROUTE: 4 => Get subtopic by id
    //    GET: "/api/subtopic/get/by-id/:id"
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<SubtopicDto> getById(
            @PathVariable String id
    ) throws Exception {
        return subtopicService.getSubtopicById(id);
    }

}

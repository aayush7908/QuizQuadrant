package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.SubtopicRequestDto;
import com.example.quizquadrant.dto.SubtopicDto;
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

    @PostMapping("/create")
    public ResponseEntity<BooleanResponseDto> create(
            @RequestBody SubtopicRequestDto subtopicRequestDto
    ) throws Exception {
        return subtopicService.create(subtopicRequestDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BooleanResponseDto> update(
            @RequestBody SubtopicRequestDto subtopicRequestDto,
            @PathVariable String id
    ) throws Exception {
        return subtopicService.update(subtopicRequestDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDto> delete(
            @PathVariable String id
    ) throws Exception {
        return subtopicService.delete(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SubtopicDto> getById(
            @PathVariable String id
    ) throws Exception {
        return subtopicService.getSubtopicById(id);
    }

}

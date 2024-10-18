//package com.example.quizquadrant.controller;
//
//import com.example.quizquadrant.dto.BooleanResponseDto;
//import com.example.quizquadrant.dto.ExamDto;
//import com.example.quizquadrant.dto.question.QuestionDto;
//import com.example.quizquadrant.dto.subject.SubjectDto;
//import com.example.quizquadrant.service.ExamService;
//import com.example.quizquadrant.service.SubjectService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@CrossOrigin
//@RestController
//@RequestMapping("/api/exam")
//public class ExamController {
//
//    private final ExamService examService;
//
//    @PostMapping("/create")
//    public ResponseEntity<BooleanResponseDto> create(
//            @RequestBody ExamDto examDto
//    ) throws Exception {
//        return examService.create(examDto);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<BooleanResponseDto> update(
//            @PathVariable String id,
//            @RequestBody ExamDto examDto
//    ) throws Exception {
//        return examService.update(examDto, id);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<BooleanResponseDto> delete(
//            @PathVariable String id
//    ) throws Exception {
//        return examService.delete(id);
//    }
//
//    @GetMapping("/get/my")
//    public ResponseEntity<List<ExamDto>> getMyExams(
//            @RequestParam("pageNumber") Integer pageNumber,
//            @RequestParam("pageSize") Integer pageSize
//    ) throws Exception {
//        return examService.getMyExams(pageNumber, pageSize);
//    }
//
//    @GetMapping("/get/by-id/{id}")
//    public ResponseEntity<ExamDto> getById(
//            @PathVariable String id
//    ) throws Exception {
//        return examService.getExamById(id);
//    }
//
//}

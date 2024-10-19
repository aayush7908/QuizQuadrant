//package com.example.quizquadrant.controller;
//
//import com.example.quizquadrant.dto.BooleanResponseDto;
//import com.example.quizquadrant.dto.ExamDto;
//import com.example.quizquadrant.dto.IdResponseDto;
//import com.example.quizquadrant.dto.question.QuestionDto;
//import com.example.quizquadrant.service.DraftExamService;
//import com.example.quizquadrant.service.ExamService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@CrossOrigin
//@RestController
//@RequestMapping("/api/draft/exam")
//public class DraftExamController {
//
//    private final DraftExamService draftExamService;
//
//    @PostMapping("/create")
//    public ResponseEntity<IdResponseDto> create(
//            @RequestBody ExamDto examDto
//    ) throws Exception {
//        return draftExamService.create(examDto);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<BooleanResponseDto> update(
//            @PathVariable String id,
//            @RequestBody ExamDto examDto
//    ) throws Exception {
//        return draftExamService.update(examDto, id);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<BooleanResponseDto> delete(
//            @PathVariable String id
//    ) throws Exception {
//        return draftExamService.delete(id);
//    }
//
//    @GetMapping("/get/my")
//    public ResponseEntity<List<ExamDto>> getMyDraftExams(
//            @RequestParam("pageNumber") Integer pageNumber,
//            @RequestParam("pageSize") Integer pageSize
//    ) throws Exception {
//        return draftExamService.getMyDraftExams(pageNumber, pageSize);
//    }
//
//    @GetMapping("/get/by-id/{id}")
//    public ResponseEntity<ExamDto> getById(
//            @PathVariable String id
//    ) throws Exception {
//        return draftExamService.getDraftExamById(id);
//    }
//
//}

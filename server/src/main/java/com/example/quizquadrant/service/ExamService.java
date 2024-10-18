//package com.example.quizquadrant.service;
//
//import com.example.quizquadrant.dto.BooleanResponseDto;
//import com.example.quizquadrant.dto.ExamDto;
//import com.example.quizquadrant.dto.subject.SubjectDto;
//import com.example.quizquadrant.model.Exam;
//import com.example.quizquadrant.model.Subject;
//import com.example.quizquadrant.model.User;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface ExamService {
//
//    //    controller service methods
//    @Transactional(rollbackFor = Exception.class)
//    ResponseEntity<BooleanResponseDto> create(
//            ExamDto examDto
//    ) throws Exception;
//
//    @Transactional(rollbackFor = Exception.class)
//    ResponseEntity<BooleanResponseDto> update(
//            ExamDto examDto,
//            String id
//    ) throws Exception;
//
//    ResponseEntity<BooleanResponseDto> delete(
//            String id
//    ) throws Exception;
//
//    ResponseEntity<List<ExamDto>> getMyExams(
//            Integer pageNumber,
//            Integer pageSize
//    ) throws Exception;
//
//    ResponseEntity<ExamDto> getExamById(
//            String id
//    ) throws Exception;
//
//    //    helper methods
//    Exam getExamById(
//            UUID id
//    ) throws Exception;
//
//    void authorizeUserExam(
//            User user,
//            Exam exam
//    ) throws Exception;
//
//}

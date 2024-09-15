//package com.example.quizquadrant.service;
//
//import com.example.quizquadrant.dto.BooleanResponseDto;
//import com.example.quizquadrant.dto.ExamDto;
//import com.example.quizquadrant.dto.IdResponseDto;
//import com.example.quizquadrant.model.DraftExam;
//import com.example.quizquadrant.model.User;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.UUID;
//
//public interface DraftExamService {
//
//    //    controller service methods
//    ResponseEntity<IdResponseDto> create(
//            ExamDto examDto
//    ) throws Exception;
//
//    ResponseEntity<BooleanResponseDto> update(
//            ExamDto examDto,
//            String id
//    ) throws Exception;
//
//    ResponseEntity<BooleanResponseDto> delete(
//            String id
//    ) throws Exception;
//
//    ResponseEntity<List<ExamDto>> getMyDraftExams(
//            Integer pageNumber,
//            Integer pageSize
//    ) throws Exception;
//
//    ResponseEntity<ExamDto> getDraftExamById(
//            String id
//    ) throws Exception;
//
//    //    helper methods
//    DraftExam getDraftExamById(
//            UUID id
//    ) throws Exception;
//
//    void deleteDraftExamById(
//            UUID id
//    ) throws Exception;
//
//    void authorizeUserDraftExam(
//            User user,
//            DraftExam draftExam
//    ) throws Exception;
//
//}

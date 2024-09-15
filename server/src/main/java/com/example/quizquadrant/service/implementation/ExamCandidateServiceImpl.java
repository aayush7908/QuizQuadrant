//package com.example.quizquadrant.service.implementation;
//
//import com.example.quizquadrant.model.*;
//import com.example.quizquadrant.repository.ExamCandidateRepository;
//import com.example.quizquadrant.service.ExamCandidateService;
//import com.example.quizquadrant.utils.error.DuplicateDataError;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ExamCandidateServiceImpl implements ExamCandidateService {
//
//    private final ExamCandidateRepository examCandidateRepository;
//
//    @Override
//    public ExamCandidate create(
//            Exam exam,
//            User user
//    ) throws Exception {
//        ExamCandidate examCandidate = null;
////        check if candidate is already enrolled in exam
//        if (!examCandidateRepository.existsByExamAndUser(exam, user)) {
////        save data
//            examCandidate = examCandidateRepository.save(
//                    ExamCandidate
//                            .builder()
//                            .exam(exam)
//                            .user(user)
//                            .isPresent(false)
//                            .build()
//            );
//        }
//
//        return examCandidate;
//    }
//
//    @Override
//    public void delete(
//            Exam exam,
//            User user
//    ) throws Exception {
//        examCandidateRepository.deleteByExamAndUser(exam, user);
//    }
//
//    private void checkExamCandidateExists(Exam exam, User user) throws Exception {
//        boolean isExamCandidatePresent = examCandidateRepository.existsByExamAndUser(exam, user);
//        if (isExamCandidatePresent) {
//            throw new DuplicateDataError("Candidate already enrolled in exam");
//        }
//    }
//
//    @Override
//    public Boolean isCandidateEnrolled(
//            Exam exam,
//            User user
//    ) throws Exception {
//        return examCandidateRepository.existsByExamAndUser(exam, user);
//    }
//
//}

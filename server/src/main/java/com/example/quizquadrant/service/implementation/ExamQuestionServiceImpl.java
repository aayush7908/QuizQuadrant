package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.ExamDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.ExamQuestion;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.repository.ExamQuestionRepository;
import com.example.quizquadrant.repository.ExamRepository;
import com.example.quizquadrant.service.ExamQuestionService;
import com.example.quizquadrant.service.ExamService;
import com.example.quizquadrant.service.QuestionService;
import com.example.quizquadrant.utils.error.DuplicateDataError;
import com.example.quizquadrant.utils.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamQuestionServiceImpl implements ExamQuestionService {

    private final ExamQuestionRepository examQuestionRepository;

    @Override
    public ExamQuestion create(
            Exam exam,
            Question question,
            Integer positiveMarks,
            Integer negativeMarks
    ) throws Exception {
//        check if question already exists in given exam
        checkExamQuestionExists(exam, question);

//        save exam question in database
        ExamQuestion examQuestion = examQuestionRepository.save(
                ExamQuestion
                        .builder()
                        .exam(exam)
                        .question(question)
                        .positiveMarks(positiveMarks)
                        .negativeMarks(negativeMarks)
                        .build()
        );

//        response
        return examQuestion;
    }

    @Override
    public ExamQuestion createOrUpdate(
            Exam exam,
            Question question,
            Integer positiveMarks,
            Integer negativeMarks
    ) throws Exception {
        ExamQuestion examQuestion = null;
//        fetch exam-question
        Optional<ExamQuestion> examQuestionOptional = examQuestionRepository.findByExamAndQuestion(exam, question);

        if (examQuestionOptional.isEmpty()) {
            examQuestion = examQuestionRepository.save(
                    ExamQuestion
                            .builder()
                            .exam(exam)
                            .question(question)
                            .positiveMarks(positiveMarks)
                            .negativeMarks(negativeMarks)
                            .build()
            );
        } else {
            examQuestion = examQuestionOptional.get();
            examQuestion.setPositiveMarks(positiveMarks);
            examQuestion.setNegativeMarks(negativeMarks);
            examQuestionRepository.save(examQuestion);
        }

//        response
        return examQuestion;
    }

    @Override
    public void delete(
            Exam exam,
            Question question
    ) throws Exception {
        examQuestionRepository.deleteByExamAndQuestion(exam, question);
    }

    private void checkExamQuestionExists(
            Exam exam,
            Question question
    ) throws Exception {
        boolean isExamQuestionPresent = examQuestionRepository.existsByExamAndQuestion(exam, question);
        if (isExamQuestionPresent) {
            throw new DuplicateDataError("Question already asked in exam");
        }
    }

}

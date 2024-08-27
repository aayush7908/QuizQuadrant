package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.ExamRepository;
import com.example.quizquadrant.service.*;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final QuestionService questionService;
    private final UserService userService;
    private final ExamQuestionService examQuestionService;
    private final ExamCandidateService examCandidateService;
    private final ValidationService validationService;

    @Override
    public ResponseEntity<BooleanResponseDto> create(
            ExamDto examDto
    ) throws Exception {
//        validate input data
        validationService.validateCreateExamInput(examDto);

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        save exam in database
        Exam exam = examRepository.save(
                Exam
                        .builder()
                        .title(examDto.title())
                        .startDateTime(examDto.startDateTime())
                        .durationInMinutes(examDto.durationInMinutes())
                        .isResultGenerated(false)
                        .totalMarks(0)
                        .lastModifiedOn(LocalDateTime.now())
                        .createdBy(user)
                        .build()
        );

//        save questions in database and mark them as exam-question
        int totalMarks = saveExamQuestionsAndReturnTotalMarks(examDto.questions(), exam, user);

//        update totalMarks in exam
        exam.setTotalMarks(totalMarks);
        examRepository.save(exam);

//        enroll candidates in exam
        for (UserDto userDto : examDto.candidates()) {
            User candidate = userService.getUserByEmail(userDto.email());
            examCandidateService.create(exam, candidate);
        }

//        response
        return ResponseEntity
                .status(200)
                .body(
                        BooleanResponseDto
                                .builder()
                                .success(true)
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> updateDetails(
            ExamDto examDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateExamDetailsInput(examDto);

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        fetch exam by id
        Exam exam = getExamById(examDto.id());

//        authorize user permissions on exam
        authorizeUserExam(user, exam);

//        update exam in database
        exam.setTitle(examDto.title());
        exam.setStartDateTime(examDto.startDateTime());
        exam.setDurationInMinutes(examDto.durationInMinutes());
        exam.setLastModifiedOn(LocalDateTime.now());
        examRepository.save(exam);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        BooleanResponseDto
                                .builder()
                                .success(true)
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> addCandidates(
            ExamDto examDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateExamCandidatesInput(examDto);

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        fetch exam by id
        Exam exam = getExamById(examDto.id());

//        authorize user permissions on exam
        authorizeUserExam(user, exam);

//        enroll candidates
        for (UserDto userDto : examDto.candidates()) {
            User candidate = userService.getUserByEmail(userDto.email());
            examCandidateService.create(exam, candidate);
        }

//        response
        return ResponseEntity
                .status(200)
                .body(
                        BooleanResponseDto
                                .builder()
                                .success(true)
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> removeCandidates(
            ExamDto examDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateExamCandidatesInput(examDto);

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        fetch exam by id
        Exam exam = getExamById(examDto.id());

//        authorize user permissions on exam
        authorizeUserExam(user, exam);

//        remove candidates
        for (UserDto userDto : examDto.candidates()) {
            User candidate = userService.getUserByEmail(userDto.email());
            examCandidateService.delete(exam, candidate);
        }

//        response
        return ResponseEntity
                .status(200)
                .body(
                        BooleanResponseDto
                                .builder()
                                .success(true)
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> addQuestions(
            ExamDto examDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateExamQuestionsInput(examDto);

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        fetch exam by id
        Exam exam = getExamById(examDto.id());

//        authorize user permissions on exam
        authorizeUserExam(user, exam);

//        save questions in database and mark them as exam-question
        int totalMarks = exam.getTotalMarks();
        totalMarks += saveExamQuestionsAndReturnTotalMarks(examDto.questions(), exam, user);

//        update totalMarks in exam
        exam.setTotalMarks(totalMarks);
        examRepository.save(exam);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        BooleanResponseDto
                                .builder()
                                .success(true)
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> removeQuestions(
            ExamDto examDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateExamQuestionsInput(examDto);

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        fetch exam by id
        Exam exam = getExamById(examDto.id());

//        authorize user permissions on exam
        authorizeUserExam(user, exam);

//        save questions in database and mark them as exam-question
        int totalMarks = exam.getTotalMarks();
        totalMarks -= deleteExamQuestionsAndReturnTotalMarks(examDto.questions(), exam);

//        update totalMarks in exam
        exam.setTotalMarks(totalMarks);
        examRepository.save(exam);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        BooleanResponseDto
                                .builder()
                                .success(true)
                                .build()
                );
    }

    @Override
    public Exam getExamById(
            UUID id
    ) throws Exception {
        Optional<Exam> examOptional = examRepository.findById(id);
        if (examOptional.isEmpty()) {
            throw new NotFoundError("Exam not found");
        }
        return examOptional.get();
    }

    @Override
    public void authorizeUserExam(
            User user,
            Exam exam
    ) throws Exception {
        if (
                user.getRole() != Role.ADMIN &&
                        !user.getId().equals(exam.getCreatedBy().getId())
        ) {
            throw new AccessDeniedError();
        }
    }

    private Integer saveExamQuestionsAndReturnTotalMarks(
            List<QuestionDto> questionDtos,
            Exam exam,
            User user
    ) throws Exception {
        int totalMarks = 0;
        for (QuestionDto questionDto : questionDtos) {
            totalMarks += questionDto.positiveMarks();
            Question question = null;
            if (questionDto.id() == null) {
                question = questionService.create(questionDto, false, user);
            } else {
                question = questionService.getQuestionById(questionDto.id());
                questionService.authorizeUserQuestion(user, question);
            }
            examQuestionService.create(
                    exam,
                    question,
                    questionDto.positiveMarks(),
                    questionDto.negativeMarks()
            );
        }
        return totalMarks;
    }

    private Integer deleteExamQuestionsAndReturnTotalMarks(
            List<QuestionDto> questionDtos,
            Exam exam
    ) throws Exception {
        int totalMarks = 0;
        for (QuestionDto questionDto : questionDtos) {
            totalMarks += questionDto.positiveMarks();
            Question question = questionService.getQuestionById(questionDto.id());
            examQuestionService.delete(
                    exam,
                    question
            );
        }
        return totalMarks;
    }

}

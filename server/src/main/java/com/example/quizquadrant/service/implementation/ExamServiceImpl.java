//package com.example.quizquadrant.service.implementation;
//
//import com.example.quizquadrant.dto.*;
//import com.example.quizquadrant.model.*;
//import com.example.quizquadrant.model.type.Role;
//import com.example.quizquadrant.repository.ExamRepository;
//import com.example.quizquadrant.service.*;
//import com.example.quizquadrant.utils.error.AccessDeniedError;
//import com.example.quizquadrant.utils.error.NotFoundError;
//import com.example.quizquadrant.utils.validation.ValidationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ExamServiceImpl implements ExamService {
//
//    private final ExamRepository examRepository;
//    private final QuestionService questionService;
//    private final UserService userService;
//    private final ExamQuestionService examQuestionService;
//    private final ExamCandidateService examCandidateService;
//    private final ValidationService validationService;
//    private final DraftExamService draftExamService;
//
//    @Override
//    public ResponseEntity<BooleanResponseDto> create(
//            ExamDto examDto
//    ) throws Exception {
////        validate input data
//        validationService.validateCreateExamInput(examDto);
//
////        fetch authenticated user
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
////        authorize user
//        userService.authorizeUser(user);
//
////        save exam in database
//        Exam exam = examRepository.save(
//                Exam
//                        .builder()
//                        .title(examDto.title())
//                        .startDateTime(examDto.startDateTime())
//                        .durationInMinutes(examDto.durationInMinutes())
//                        .isResultGenerated(false)
//                        .totalMarks(0)
//                        .lastModifiedOn(LocalDateTime.now())
//                        .createdBy(user)
//                        .build()
//        );
//
////        save questions in database and mark them as exam-question
//        int totalMarks = saveExamQuestionsAndReturnTotalMarks(examDto.questions(), exam, user);
//
////        update totalMarks in exam
//        exam.setTotalMarks(totalMarks);
//        examRepository.save(exam);
//
////        enroll candidates in exam
//        for (UserDto userDto : examDto.candidates()) {
//            try {
//                User candidate = userService.getUserByEmail(userDto.email());
//                examCandidateService.create(exam, candidate);
//            } catch (Exception ignored) {
//            }
//        }
//
////        delete draft exam if present
//        if (examDto.id() != null) {
//            draftExamService.deleteDraftExamById(examDto.id());
//        }
//
////        response
//        return ResponseEntity
//                .status(200)
//                .body(
//                        BooleanResponseDto
//                                .builder()
//                                .success(true)
//                                .build()
//                );
//    }
//
//    @Override
//    public ResponseEntity<BooleanResponseDto> update(
//            ExamDto examDto,
//            String id
//    ) throws Exception {
////        validate input data
//        validationService.validateCreateExamInput(examDto);
//
////        find authenticated user
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
////        authorize user
//        userService.authorizeUser(user);
//
////        fetch exam by id
//        Exam exam = getExamById(UUID.fromString(id));
//
////        check user permissions on exam
//        authorizeUserExam(user, exam);
//
////        save questions in database and mark them as exam-question
//        int totalMarks = saveExamQuestionsAndReturnTotalMarks(examDto.questions(), exam, user);
//
////        enroll candidates in exam
//        for (UserDto userDto : examDto.candidates()) {
//            try {
//                User candidate = userService.getUserByEmail(userDto.email());
//                examCandidateService.create(exam, candidate);
//            } catch (Exception ignored) {
//            }
//        }
//
////        update exam data and save in database
//        exam.setTitle(examDto.title());
//        exam.setStartDateTime(examDto.startDateTime());
//        exam.setDurationInMinutes(examDto.durationInMinutes());
//        exam.setLastModifiedOn(LocalDateTime.now());
//        exam.setTotalMarks(totalMarks);
//        examRepository.save(exam);
//
////        response
//        return ResponseEntity
//                .status(200)
//                .body(
//                        BooleanResponseDto
//                                .builder()
//                                .success(true)
//                                .build()
//                );
//    }
//
//    @Override
//    public ResponseEntity<BooleanResponseDto> delete(
//            String id
//    ) throws Exception {
////        find authenticated user
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
////        authorize user
//        userService.authorizeUser(user);
//
////        fetch exam by id
//        Exam exam = getExamById(UUID.fromString(id));
//
////        check user permissions on exam
//        authorizeUserExam(user, exam);
//
////        delete exam from database
//        examRepository.delete(exam);
//
////        response
//        return ResponseEntity
//                .status(200)
//                .body(
//                        BooleanResponseDto
//                                .builder()
//                                .success(true)
//                                .build()
//                );
//    }
//
//    @Override
//    public ResponseEntity<List<ExamDto>> getMyExams(
//            Integer pageNumber,
//            Integer pageSize
//    ) throws Exception {
////        validate pageSize
//        validationService.validatePageSize(pageSize);
//
////        find authenticated user
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
////        authorize user
//        userService.authorizeUser(user);
//
////        fetch exams created by user
//        List<Exam> exams = getExamsByUser(user, pageNumber, pageSize);
//
////        create list of exam dto
//        List<ExamDto> examDtos = new ArrayList<>();
//        for (Exam exam : exams) {
////            create list of questions
//            List<QuestionDto> questionDtos = new ArrayList<>();
//            for (ExamQuestion examQuestion : exam.getQuestions()) {
//                questionDtos.add(
//                        QuestionDto.builder().build()
//                );
//            }
//
////            create list of candidates
//            List<UserDto> userDtos = new ArrayList<>();
//            for (ExamCandidate examCandidate : exam.getCandidates()) {
//                userDtos.add(
//                        UserDto.builder().build()
//                );
//            }
//
//            examDtos.add(
//                    ExamDto
//                            .builder()
//                            .id(exam.getId())
//                            .title(exam.getTitle())
//                            .startDateTime(exam.getStartDateTime())
//                            .durationInMinutes(exam.getDurationInMinutes())
//                            .isResultGenerated(exam.getIsResultGenerated())
//                            .totalMarks(exam.getTotalMarks())
//                            .lastModifiedOn(exam.getLastModifiedOn())
//                            .questions(questionDtos)
//                            .candidates(userDtos)
//                            .build()
//            );
//        }
//
////        if pageNumber is 0, calculate total number of exams created by user
////        and put it into first exam
//        if (pageNumber == 0) {
//            int totalExams = examRepository.countByCreatedBy(user);
//            examDtos.add(0, ExamDto
//                    .builder()
//                    .createdBy(
//                            UserDto
//                                    .builder()
//                                    .totalExams(totalExams)
//                                    .build()
//                    )
//                    .build()
//            );
//        }
//
////        response
//        return ResponseEntity
//                .status(200)
//                .body(examDtos);
//    }
//
//    @Override
//    public ResponseEntity<ExamDto> getExamById(
//            String id
//    ) throws Exception {
////        find authenticated user
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
////        authorize user
//        userService.authorizeUser(user);
//
////        fetch exam by id
//        Exam exam = getExamById(UUID.fromString(id));
//
////        check user permissions on exam
//        authorizeUserExam(user, exam);
//
////        create question dtos
//        List<QuestionDto> questionDtos = new ArrayList<>();
//        for (ExamQuestion examQuestion : exam.getQuestions()) {
//            Question question = questionService.getQuestionById(examQuestion.getQuestion().getId());
//
////        create list of option dtos
//            List<OptionDto> optionDtos = new ArrayList<>();
//            for (Option option : question.getOptions()) {
////            create option dto and add it to option dto list
//                optionDtos.add(
//                        OptionDto
//                                .builder()
//                                .id(option.getId())
//                                .statement(option.getStatement())
//                                .imageUrl(option.getImageUrl())
//                                .isCorrect(option.getIsCorrect())
//                                .build()
//                );
//            }
//
////        create subtopic dto
//            SubtopicDto subtopicDto = SubtopicDto
//                    .builder()
//                    .id(question.getSubtopic().getId())
//                    .name(question.getSubtopic().getName())
//                    .subject(
//                            SubjectDto
//                                    .builder()
//                                    .id(question.getSubtopic().getSubject().getId())
//                                    .name(question.getSubtopic().getSubject().getName())
//                                    .build()
//                    )
//                    .build();
//
////        create solution dto
//            SolutionDto solutionDto = SolutionDto
//                    .builder()
//                    .id(question.getSolution().getId())
//                    .statement(question.getSolution().getStatement())
//                    .imageUrl(question.getSolution().getImageUrl())
//                    .build();
//
////        create question dto
//            QuestionDto questionDto = QuestionDto
//                    .builder()
//                    .id(question.getId())
//                    .positiveMarks(examQuestion.getPositiveMarks())
//                    .negativeMarks(examQuestion.getNegativeMarks())
//                    .type(question.getType().name())
//                    .isPublic(question.getIsPublic())
//                    .statement(question.getStatement())
//                    .imageUrl(question.getImageUrl())
//                    .lastModifiedOn(question.getLastModifiedOn())
//                    .subtopic(subtopicDto)
//                    .options(optionDtos)
//                    .solution(solutionDto)
//                    .build();
//
////            add question dto to list
//            questionDtos.add(questionDto);
//        }
//
////        create candidate dtos
//        List<UserDto> userDtos = new ArrayList<>();
//        for (ExamCandidate examCandidate : exam.getCandidates()) {
//            User candidate = userService.getUserById(examCandidate.getUser().getId());
//            userDtos.add(
//                    UserDto
//                            .builder()
//                            .id(candidate.getId())
//                            .email(candidate.getEmail())
//                            .build()
//            );
//        }
//
////        create exam dto
//        ExamDto examDto = ExamDto
//                .builder()
//                .id(exam.getId())
//                .title(exam.getTitle())
//                .startDateTime(exam.getStartDateTime())
//                .durationInMinutes(exam.getDurationInMinutes())
//                .isResultGenerated(exam.getIsResultGenerated())
//                .lastModifiedOn(exam.getLastModifiedOn())
//                .questions(questionDtos)
//                .candidates(userDtos)
//                .build();
//
////        response
//        return ResponseEntity
//                .status(200)
//                .body(examDto);
//    }
//
//    @Override
//    public Exam getExamById(
//            UUID id
//    ) throws Exception {
//        Optional<Exam> examOptional = examRepository.findById(id);
//        if (examOptional.isEmpty()) {
//            throw new NotFoundError("Exam not found");
//        }
//        return examOptional.get();
//    }
//
//    @Override
//    public void authorizeUserExam(
//            User user,
//            Exam exam
//    ) throws Exception {
//        if (
//                user.getRole() != Role.ADMIN &&
//                        !user.getId().equals(exam.getCreatedBy().getId())
//        ) {
//            throw new AccessDeniedError();
//        }
//    }
//
//    private Integer saveExamQuestionsAndReturnTotalMarks(
//            List<QuestionDto> questionDtos,
//            Exam exam,
//            User user
//    ) throws Exception {
//        int totalMarks = 0;
//        for (QuestionDto questionDto : questionDtos) {
//            totalMarks += questionDto.positiveMarks();
//            Question question = null;
//            if (
//                    questionDto.id() == null ||
//                            questionDto.id().toString().isEmpty()
//            ) {
//                question = questionService.createQuestion(questionDto, user);
//            } else {
//                question = questionService.getQuestionById(questionDto.id());
//                questionService.authorizeUserQuestion(user, question);
//            }
//            examQuestionService.createOrUpdate(
//                    exam,
//                    question,
//                    questionDto.positiveMarks(),
//                    questionDto.negativeMarks()
//            );
//        }
//        return totalMarks;
//    }
//
//    private Integer deleteExamQuestionsAndReturnTotalMarks(
//            List<QuestionDto> questionDtos,
//            Exam exam
//    ) throws Exception {
//        int totalMarks = 0;
//        for (QuestionDto questionDto : questionDtos) {
//            totalMarks += questionDto.positiveMarks();
//            Question question = questionService.getQuestionById(questionDto.id());
//            examQuestionService.delete(
//                    exam,
//                    question
//            );
//        }
//        return totalMarks;
//    }
//
//    private List<Exam> getExamsByUser(
//            User user,
//            Integer pageNumber,
//            Integer pageSize
//    ) throws Exception {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<Exam> examPage = examRepository.findByCreatedBy(user, pageable);
//        return examPage.getContent();
//    }
//
//}

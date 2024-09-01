package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.*;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.QuestionRepository;
import com.example.quizquadrant.service.*;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ValidationService validationService;
    private final SubjectService subjectService;
    private final SubtopicService subtopicService;
    private final SolutionService solutionService;
    private final OptionService optionService;
    private final UserService userService;
    private final DraftQuestionService draftQuestionService;

    @Override
    public ResponseEntity<BooleanResponseDto> create(
            QuestionDto questionDto
    ) throws Exception {
//        validate input data
        validationService.validateCreateQuestionInput(questionDto);

//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        save question, solution and options in database
        Question question = create(questionDto, questionDto.isPublic(), user);

//        delete draft question if exists
        if (
                questionDto.id() != null &&
                        !questionDto.id().toString().isEmpty()
        ) {
            draftQuestionService.deleteDraftQuestionById(questionDto.id());
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
    public ResponseEntity<BooleanResponseDto> update(
            QuestionDto questionDto,
            String id
    ) throws Exception {
//        validate input data
        validationService.validateUpdateQuestionInput(questionDto);

//        find subtopic by id
        Subtopic subtopic = subtopicService.getSubtopicById(questionDto.subtopic().id());

//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        fetch question by id
        Question question = getQuestionById(UUID.fromString(id));

//        authorize user
        userService.authorizeUser(user);

//        authorize user permissions on question
        authorizeUserQuestion(user, question);

//        update question in database
        question.setType(QuestionType.valueOf(questionDto.type()));
        question.setIsPublic(questionDto.isPublic());
        question.setStatement(questionDto.statement());
        question.setImageUrl(questionDto.imageUrl());
        question.setLastModifiedOn(LocalDateTime.now());
        question.setSubtopic(subtopic);
        questionRepository.save(question);

//        update solution in database
        solutionService.update(questionDto.solution());

//        update options in database
        for (OptionDto optionDto : questionDto.options()) {
            optionService.update(optionDto);
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
    public ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception {
//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        fetch question by id
        Question question = getQuestionById(UUID.fromString(id));

//        authorize user
        userService.authorizeUser(user);

//        authorize user permissions on question
        authorizeUserQuestion(user, question);

//        delete question from database
        questionRepository.delete(question);

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
    public ResponseEntity<List<QuestionDto>> getMyQuestions(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate pageSize
        validationService.validatePageSize(pageSize);

//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        fetch questions created by user
        List<Question> questions = getQuestionsByUser(user, pageNumber, pageSize);

//        create list of question dto
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
//            create question dto and add it to question dto list
            questionDtos.add(
                    createQuestionDtoFromQuestion(question)
            );
        }

//        if pageNumber is 0, calculate total number of questions created by user
//        and put it into first question
        if (pageNumber == 0) {
            int totalQuestions = questionRepository.countByCreatedBy(user);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .createdBy(
                            UserDto
                                    .builder()
                                    .totalQuestions(totalQuestions)
                                    .build()
                    )
                    .build());
        }

//        response
        return ResponseEntity
                .status(200)
                .body(questionDtos);
    }

    @Override
    public ResponseEntity<QuestionDto> getQuestionById(
            String id
    ) throws Exception {
//        fetch question by id
        Question question = getQuestionById(UUID.fromString(id));

//        create question dto
        QuestionDto questionDto = createQuestionDtoFromQuestion(question);

//        response
        return ResponseEntity
                .status(200)
                .body(questionDto);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getQuestionsBySubject(
            String id,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate page size
        validationService.validatePageSize(pageSize);

//        fetch subject by id
        Subject subject = subjectService.getSubjectById(UUID.fromString(id));

//        fetch subtopics by subject
        List<Subtopic> subtopics = subtopicService.getSubtopicsBySubject(subject);

//        fetch questions by subtopics
        List<Question> questions = getQuestionsBySubtopics(subtopics, pageNumber, pageSize);

//        create list of question dto
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
//            create question dto and add it to question dto list
            questionDtos.add(
                    createQuestionDtoFromQuestion(question)
            );
        }

//        if pageNumber is 0, calculate total number of questions within the subject
//        and put it into first question
        if (pageNumber == 0) {
            int totalQuestions = questionRepository.countBySubtopics(subtopics);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .subtopic(
                            SubtopicDto
                                    .builder()
                                    .subject(
                                            SubjectDto
                                                    .builder()
                                                    .totalQuestions(totalQuestions)
                                                    .build()
                                    )
                                    .build()
                    )
                    .build());
        }

//        response
        return ResponseEntity
                .status(200)
                .body(questionDtos);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getQuestionsBySubtopic(
            String id,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate page size
        validationService.validatePageSize(pageSize);

//        fetch subtopic by id
        Subtopic subtopic = subtopicService.getSubtopicById(UUID.fromString(id));

//        fetch questions by subtopic
        List<Question> questions = getQuestionsBySubtopic(subtopic, pageNumber, pageSize);

//        create list of question dto
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
//            create question dto and add it to question dto list
            questionDtos.add(
                    createQuestionDtoFromQuestion(question)
            );
        }

//        if pageNumber is 0, calculate total number of questions within the subtopic
//        and put it into first question
        if (pageNumber == 0) {
            int totalQuestions = questionRepository.countBySubtopic(subtopic);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .subtopic(
                            SubtopicDto
                                    .builder()
                                    .totalQuestions(totalQuestions)
                                    .build()
                    )
                    .build());
        }

//        response
        return ResponseEntity
                .status(200)
                .body(questionDtos);
    }

    @Override
    public Question getQuestionById(
            UUID id
    ) throws Exception {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isEmpty()) {
            throw new NotFoundError("Question not found");
        }
        return questionOptional.get();
    }

    @Override
    public Question create(
            QuestionDto questionDto,
            Boolean isPublic,
            User user
    ) throws Exception {
//        validate question type
        validationService.validateQuestionType(questionDto.type());

//        find subtopic by id
        Subtopic subtopic = subtopicService.getSubtopicById(questionDto.subtopic().id());

//        save question in database
        Question question = questionRepository.save(
                Question
                        .builder()
                        .type(QuestionType.valueOf(questionDto.type()))
                        .isPublic(isPublic)
                        .statement(questionDto.statement())
                        .imageUrl(questionDto.imageUrl())
                        .lastModifiedOn(LocalDateTime.now())
                        .subtopic(subtopic)
                        .createdBy(user)
                        .build()
        );

//        save solution in database
        solutionService.create(questionDto.solution(), question);

//        save options in database
        for (OptionDto optionDto : questionDto.options()) {
            optionService.create(optionDto, question);
        }

//        response
        return question;
    }

    @Override
    public void authorizeUserQuestion(
            User user,
            Question question
    ) throws Exception {
        if (
                user.getRole() != Role.ADMIN &&
                        !user.getId().equals(question.getCreatedBy().getId())
        ) {
            throw new AccessDeniedError();
        }
    }


//    private methods to reduce duplicate code

    private List<Question> getQuestionsByUser(
            User user,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = questionRepository.findByCreatedBy(user, pageable);
        return questionPage.getContent();
    }

    private List<Question> getQuestionsBySubtopics(
            List<Subtopic> subtopics,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = questionRepository.findBySubtopics(subtopics, pageable);
        return questionPage.getContent();
    }

    private List<Question> getQuestionsBySubtopic(
            Subtopic subtopic,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = questionRepository.findBySubtopics(List.of(subtopic), pageable);
        return questionPage.getContent();
    }

    @Override
    public QuestionDto createQuestionDtoFromQuestion(
            Question question
    ) {
//        create list of option dtos
        List<OptionDto> optionDtos = new ArrayList<>();
        for (Option option : question.getOptions()) {
//            create option dto and add it to option dto list
            optionDtos.add(
                    OptionDto
                            .builder()
                            .id(option.getId())
                            .statement(option.getStatement())
                            .imageUrl(option.getImageUrl())
                            .isCorrect(option.getIsCorrect())
                            .build()
            );
        }

//        create subtopic dto
        SubtopicDto subtopicDto = SubtopicDto
                .builder()
                .id(question.getSubtopic().getId())
                .name(question.getSubtopic().getName())
                .subject(
                        SubjectDto
                                .builder()
                                .id(question.getSubtopic().getSubject().getId())
                                .name(question.getSubtopic().getSubject().getName())
                                .build()
                )
                .build();

//        create solution dto
        SolutionDto solutionDto = SolutionDto
                .builder()
                .id(question.getSolution().getId())
                .statement(question.getSolution().getStatement())
                .imageUrl(question.getSolution().getImageUrl())
                .build();

//        create question dto and return
        return QuestionDto
                .builder()
                .id(question.getId())
                .type(question.getType().name())
                .isPublic(question.getIsPublic())
                .statement(question.getStatement())
                .imageUrl(question.getImageUrl())
                .lastModifiedOn(question.getLastModifiedOn())
                .subtopic(subtopicDto)
                .options(optionDtos)
                .solution(solutionDto)
                .build();
    }
}

package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.IdResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.QuestionDtoMapper;
import com.example.quizquadrant.dto.question.QuestionCreatedByDto;
import com.example.quizquadrant.dto.question.QuestionDto;
import com.example.quizquadrant.dto.question.QuestionRequestDto;
import com.example.quizquadrant.dto.user.UserDto;
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
    private final SavedQuestionService savedQuestionService;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;
    private final QuestionDtoMapper questionDtoMapper;
    private final IdResponseDtoMapper idResponseDtoMapper;

    //    controller service methods
    @Override
    public ResponseEntity<IdResponseDto> create(
            QuestionRequestDto questionRequestDto,
            String draftId
    ) throws Exception {
//        validate input data
        validationService.validateQuestionRequestInput(questionRequestDto);

//        find authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user
        userService.authorizeUser(user);

//        save question in database
        Question question = create(questionRequestDto, user);

//        delete draft question if exists
        if (draftId != null && !draftId.isEmpty()) {
            draftQuestionService
                    .deleteDraftQuestion(
                            validationService.validateAndGetUUID(draftId)
                    );
        }

//        response
        return ResponseEntity
                .status(200)
                .body(
                        idResponseDtoMapper
                                .toDto(question)
                );
    }

    @Override
    public ResponseEntity<IdResponseDto> update(
            QuestionRequestDto questionRequestDto,
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        validate input data
        validationService.validateQuestionRequestInput(questionRequestDto);

//        find authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user
        userService.authorizeUser(user);

//        fetch question by id
        Question question = getQuestionById(uuid);

//        authorize user permissions on question
        authorizeUserQuestion(user, question);

//        update question in database
        question = update(questionRequestDto, question);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        idResponseDtoMapper
                                .toDto(question)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        find authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user
        userService.authorizeUser(user);

//        fetch question by id
        Question question = getQuestionById(uuid);

//        authorize user permissions on question
        authorizeUserQuestion(user, question);

//        delete question from database
        deleteQuestion(uuid);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> save(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        find authenticated user
        User user = userService.getAuthenticatedUser();
        user = userService.getUserById(user.getId());

//        authorize user
        userService.authorizeUser(user);

//        fetch question by id
        Question question = getQuestionById(uuid);

//        save question
        SavedQuestion savedQuestion = savedQuestionService.saveQuestion(user, question);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> unsave(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        find authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user
        userService.authorizeUser(user);

//        fetch question by id
        Question question = getQuestionById(uuid);

//        unsave question
        savedQuestionService.unsaveQuestion(user, question);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getMyCreatedQuestions(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate pageNumber and pageSize
        validationService.validatePageNumber(pageNumber);
        validationService.validatePageSize(pageSize);

//        find authenticated user
        User user = userService.getAuthenticatedUser();

//        fetch questions created by user
        List<Question> questions = getQuestionsByUser(user, pageNumber, pageSize);

//        create list of question dto
        List<QuestionDto> questionDtos = questionDtoMapper.toDtos(questions);

//        if pageNumber is 0, calculate total number of questions created by user
//        and put it into first question
        if (pageNumber == 0) {
            int totalQuestions = countQuestionsCreatedByUser(user);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .totalQuestions(totalQuestions)
                    .build());
        }

//        response
        return ResponseEntity
                .status(200)
                .body(questionDtos);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getMySavedQuestions(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate pageNumber and pageSize
        validationService.validatePageNumber(pageNumber);
        validationService.validatePageSize(pageSize);

//        find authenticated user
        User user = userService.getAuthenticatedUser();

//        fetch questions saved by user
        List<SavedQuestion> savedQuestions = savedQuestionService.getSavedQuestionsByUser(user, pageNumber, pageSize);

//        create list of question dto
        List<Question> questions = savedQuestions.stream().map(SavedQuestion::getQuestion).toList();
        List<QuestionDto> questionDtos = questionDtoMapper.toDtos(questions);

//        if pageNumber is 0, calculate total number of questions saved by user
//        and put it into first question
        if (pageNumber == 0) {
            int totalQuestions = savedQuestionService.countQuestionsSavedByUser(user);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .totalQuestions(totalQuestions)
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
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch question by id
        Question question = getQuestionById(uuid);

//        create question dto
        QuestionDto questionDto = questionDtoMapper.toDto(question);

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
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        validate pageNumber and pageSize
        validationService.validatePageNumber(pageNumber);
        validationService.validatePageSize(pageSize);

//        fetch subject by id
        Subject subject = subjectService.getSubjectById(uuid);

//        fetch questions by subtopics
        List<Question> questions = getQuestionsBySubject(subject, pageNumber, pageSize);

//        create list of question dto
        List<QuestionDto> questionDtos = questionDtoMapper.toDtos(questions);

//        if pageNumber is 0, calculate total number of questions within the subject
//        and put it into first question
        if (pageNumber == 0) {
            int totalQuestions = countQuestionsBySubject(subject);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .totalQuestions(totalQuestions)
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
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        validate pageNumber and pageSize
        validationService.validatePageNumber(pageNumber);
        validationService.validatePageSize(pageSize);

//        fetch subtopic by id
        Subtopic subtopic = subtopicService.getSubtopicById(uuid);

//        fetch questions by subtopic
        List<Question> questions = getQuestionsBySubtopic(subtopic, pageNumber, pageSize);

//        create list of question dto
        List<QuestionDto> questionDtos = questionDtoMapper.toDtos(questions);

//        if pageNumber is 0, calculate total number of questions within the subtopic
//        and put it into first question
        if (pageNumber == 0) {
            int totalQuestions = countQuestionsBySubtopic(subtopic);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .totalQuestions(totalQuestions)
                    .build());
        }

//        response
        return ResponseEntity
                .status(200)
                .body(questionDtos);
    }


    //    repository access methods
    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(UUID id) {
        questionRepository.deleteById(id);
    }

    @Override
    public int countQuestionsCreatedByUser(User user) {
        return questionRepository.countByCreatedBy(user);
    }

    @Override
    public int countQuestionsBySubject(Subject subject) {
        return questionRepository.countBySubtopics(subject.getSubtopics());
    }

    @Override
    public int countQuestionsBySubtopic(Subtopic subtopic) {
        return questionRepository.countBySubtopic(subtopic);
    }

    @Override
    public Question getQuestionById(
            UUID id
    ) throws Exception {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isEmpty()) {
            throw new NotFoundError("Question Not Found");
        }
        return questionOptional.get();
    }

    @Override
    public List<Question> getQuestionsByUser(
            User user,
            Integer pageNumber,
            Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = questionRepository.findByCreatedBy(user, pageable);
        return questionPage.getContent();
    }

    private List<Question> getQuestionsBySubject(
            Subject subject,
            Integer pageNumber,
            Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = questionRepository.findBySubtopics(subject.getSubtopics(), pageable);
        return questionPage.getContent();
    }

    private List<Question> getQuestionsBySubtopic(
            Subtopic subtopic,
            Integer pageNumber,
            Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Question> questionPage = questionRepository.findBySubtopics(List.of(subtopic), pageable);
        return questionPage.getContent();
    }

    //    helper methods
    @Override
    public Question create(
            QuestionRequestDto questionRequestDto,
            User user
    ) throws Exception {
//        find subtopic by id
        Subtopic subtopic = subtopicService.getSubtopicById(
                questionRequestDto.subtopic().id()
        );

//        save question in database
        Question question = createQuestion(
                Question
                        .builder()
                        .type(QuestionType.valueOf(questionRequestDto.type()))
                        .isPublic(
                                questionRequestDto.isPublic() != null &&
                                        questionRequestDto.isPublic()
                        )
                        .statement(questionRequestDto.statement())
                        .imageUrl(questionRequestDto.imageUrl())
                        .lastModifiedOn(LocalDateTime.now())
                        .subtopic(subtopic)
                        .createdBy(user)
                        .build()
        );

//        save solution in database
        solutionService.create(
                questionRequestDto.solution(),
                question
        );

//        save options in database
        optionService.create(
                questionRequestDto.options(),
                question
        );

//        response
        return question;
    }

    @Override
    public Question update(
            QuestionRequestDto questionRequestDto,
            Question question
    ) throws Exception {
//        find subtopic by id
        Subtopic subtopic = subtopicService.getSubtopicById(
                questionRequestDto.subtopic().id()
        );

//        update question
        question.setType(QuestionType.valueOf(questionRequestDto.type()));
        question.setIsPublic(questionRequestDto.isPublic());
        question.setStatement(questionRequestDto.statement());
        question.setImageUrl(questionRequestDto.imageUrl());
        question.setLastModifiedOn(LocalDateTime.now());
        question.setSubtopic(subtopic);
        question = updateQuestion(question);

//        save solution in database
        solutionService.update(
                questionRequestDto.solution(),
                question
        );

//        save options in database
        optionService.update(
                questionRequestDto.options(),
                question
        );

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
}

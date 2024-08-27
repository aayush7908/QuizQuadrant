package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.QuestionRepository;
import com.example.quizquadrant.service.*;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
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
    private final SubtopicService subtopicService;
    private final SolutionService solutionService;
    private final OptionService optionService;
    private final UserService userService;

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
        Question question = getById(UUID.fromString(id));

//        authorize user
        userService.authorizeUser(user);

//        authorize user permissions on question
        authorizeUserQuestion(user, question);

//        validate question type
        validationService.validateQuestionType(questionDto.type());

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
        Question question = getById(UUID.fromString(id));

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
    public ResponseEntity<List<QuestionDto>> getMyQuestions() throws Exception {
//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        fetch questions created by user
        List<Question> questions = questionRepository.findByCreatedBy(user);

//        create list of question dto
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
//            create list of option dtos
            List<OptionDto> optionDtos = new ArrayList<>();
            for (Option option : question.getOptions()) {
//                create option dto and add it to option dto list
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

//            create subtopic dto
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

//            create solution dto
            SolutionDto solutionDto = SolutionDto
                    .builder()
                    .id(question.getSolution().getId())
                    .statement(question.getSolution().getStatement())
                    .imageUrl(question.getSolution().getImageUrl())
                    .build();

//            create question dto and add it to question dto list
            questionDtos.add(
                    QuestionDto
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
                            .build()
            );
        }

//        response
        return ResponseEntity
                .status(200)
                .body(questionDtos);
    }

    @Override
    public ResponseEntity<QuestionDto> getById(
            String id
    ) throws Exception {
//        fetch question by id
        Question question = getById(UUID.fromString(id));

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

//        create question dto
        QuestionDto questionDto = QuestionDto
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

//        response
        return ResponseEntity
                .status(200)
                .body(questionDto);
    }

    @Override
    public Question getById(
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

}

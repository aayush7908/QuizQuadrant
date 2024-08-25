package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.OptionDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.repository.QuestionRepository;
import com.example.quizquadrant.service.OptionService;
import com.example.quizquadrant.service.QuestionService;
import com.example.quizquadrant.service.SolutionService;
import com.example.quizquadrant.service.SubtopicService;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public ResponseEntity<BooleanResponseDto> create(
            QuestionDto questionDto
    ) throws Exception {
//        validate input data
        validationService.validateCreateQuestionInput(questionDto);

//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
            QuestionDto questionDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateQuestionInput(questionDto);

//        find subtopic by id
        Subtopic subtopic = subtopicService.getSubtopicById(questionDto.subtopic().id());

//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        fetch question by id
        Question question = getQuestionById(questionDto.id());

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
        Question question = getQuestionById(UUID.fromString(id));

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
        if (!user.getId().equals(question.getCreatedBy().getId())) {
            throw new AccessDeniedError();
        }
    }

}

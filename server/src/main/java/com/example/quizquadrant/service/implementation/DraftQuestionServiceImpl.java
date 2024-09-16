package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.model.DraftQuestion;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.DraftQuestionRepository;
import com.example.quizquadrant.service.DraftQuestionService;
import com.example.quizquadrant.service.UserService;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.BadRequestError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DraftQuestionServiceImpl implements DraftQuestionService {

    private final DraftQuestionRepository draftQuestionRepository;
    private final ValidationService validationService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;

    @Override
    public ResponseEntity<IdResponseDto> create(
            QuestionDto questionDto
    ) throws Exception {
//        fetch authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user
        userService.authorizeUser(user);

//        convert dto to json
        String data = "";
        try {
            data = objectMapper.writeValueAsString(questionDto);
        } catch (Exception e) {
            throw new BadRequestError("Invalid Data");
        }

//        save draft in database
        DraftQuestion draftQuestion = createDraftQuestion(
                DraftQuestion
                        .builder()
                        .data(data)
                        .lastModifiedOn(LocalDateTime.now())
                        .createdBy(user)
                        .build()
        );

//        response
        return ResponseEntity
                .status(200)
                .body(
                        IdResponseDto
                                .builder()
                                .id(draftQuestion.getId())
                                .build()
                );
    }

    @Override
    public ResponseEntity<IdResponseDto> update(
            QuestionDto questionDto,
            String id
    ) throws Exception {
//        fetch draft-question by id
        DraftQuestion draftQuestion = getDraftQuestionById(UUID.fromString(id));

//        fetch authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user
        userService.authorizeUser(user);

//        authorize user permission on draft
        authorizeUserDraftQuestion(user, draftQuestion);

//        convert dto to json
        String data = "";
        try {
            data = objectMapper.writeValueAsString(questionDto);
        } catch (Exception e) {
            throw new BadRequestError("Invalid Data");
        }

//        update and save draft-exam in database
        draftQuestion.setData(data);
        draftQuestion.setLastModifiedOn(LocalDateTime.now());
        updateDraftQuestion(draftQuestion);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        IdResponseDto
                                .builder()
                                .id(draftQuestion.getId())
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch draft-question by id
        DraftQuestion draftQuestion = getDraftQuestionById(uuid);

//        fetch authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user
        userService.authorizeUser(user);

//        authorize user permission on draft
        authorizeUserDraftQuestion(user, draftQuestion);

//        delete draft
        deleteDraftQuestion(uuid);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getMyDraftQuestions(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate pageSize
        validationService.validatePageNumber(pageNumber);
        validationService.validatePageSize(pageSize);

//        find authenticated user
        User user = userService.getAuthenticatedUser();

//        fetch draft-questions created by user
        List<DraftQuestion> draftQuestions = getDraftQuestionsByUser(user, pageNumber, pageSize);

//        create list of exam dto
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (DraftQuestion draftQuestion : draftQuestions) {
            QuestionDto questionDto = objectMapper.readValue(draftQuestion.getData(), QuestionDto.class);
            questionDtos.add(
                    QuestionDto
                            .builder()
                            .id(draftQuestion.getId())
                            .statement(questionDto.statement())
                            .lastModifiedOn(draftQuestion.getLastModifiedOn())
                            .build()
            );
        }

//        if pageNumber is 0, calculate total number of drafts created by user
//        and put it into first draft
        if (pageNumber == 0) {
            int totalDrafts = draftQuestionRepository.countByCreatedBy(user);
            questionDtos.add(0, QuestionDto
                    .builder()
                    .createdBy(
                            UserDto
                                    .builder()
                                    .totalDraftQuestions(totalDrafts)
                                    .build()
                    )
                    .build()
            );
        }

//        response
        return ResponseEntity
                .status(200)
                .body(questionDtos);
    }

    @Override
    public ResponseEntity<QuestionDto> getDraftQuestionById(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch draft-question by id
        DraftQuestion draftQuestion = getDraftQuestionById(uuid);

//        fetch authenticated user
        User user = userService.getAuthenticatedUser();

//        authorize user permission on draft
        authorizeUserDraftQuestion(user, draftQuestion);

//        create dto
        QuestionDto questionDto = createQuestionDtoFromDraftQuestionData(draftQuestion);

//        response
        return ResponseEntity
                .status(200)
                .body(questionDto);
    }


    //    repository access methods
    @Override
    public DraftQuestion createDraftQuestion(DraftQuestion draftQuestion) {
        return draftQuestionRepository.save(draftQuestion);
    }

    @Override
    public DraftQuestion updateDraftQuestion(DraftQuestion draftQuestion) {
        return draftQuestionRepository.save(draftQuestion);
    }

    @Override
    public void deleteDraftQuestion(UUID id) {
        draftQuestionRepository.deleteById(id);
    }

    @Override
    public DraftQuestion getDraftQuestionById(
            UUID id
    ) throws Exception {
        Optional<DraftQuestion> draftQuestionOptional = draftQuestionRepository.findById(id);
        if (draftQuestionOptional.isEmpty()) {
            throw new NotFoundError("Draft not found");
        }
        return draftQuestionOptional.get();
    }

    private List<DraftQuestion> getDraftQuestionsByUser(
            User user,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<DraftQuestion> draftQuestionPage = draftQuestionRepository.findByCreatedBy(user, pageable);
        return draftQuestionPage.getContent();
    }


    //    helper methods
    @Override
    public void authorizeUserDraftQuestion(
            User user,
            DraftQuestion draftQuestion
    ) throws Exception {
        if (
                user.getRole() != Role.ADMIN &&
                        !user.getId().equals(draftQuestion.getCreatedBy().getId())
        ) {
            throw new AccessDeniedError();
        }
    }

    private QuestionDto createQuestionDtoFromDraftQuestionData(
            DraftQuestion draftQuestion
    ) throws Exception {
        QuestionDto questionDto = objectMapper.readValue(draftQuestion.getData(), QuestionDto.class);
        return QuestionDto
                .builder()
                .id(draftQuestion.getId())
                .type(questionDto.type())
                .isPublic(questionDto.isPublic())
                .statement(questionDto.statement())
                .imageUrl(questionDto.imageUrl())
                .lastModifiedOn(questionDto.lastModifiedOn())
                .subtopic(
                        SubtopicDto
                                .builder()
                                .id(questionDto.subtopic().id())
                                .subject(
                                        SubjectDto
                                                .builder()
                                                .id(questionDto.subtopic().subject().id())
                                                .build()
                                )
                                .build()
                )
                .options(questionDto.options())
                .solution(questionDto.solution())
                .build();
    }

}

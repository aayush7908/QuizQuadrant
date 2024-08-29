package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.DraftExam;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.DraftExamRepository;
import com.example.quizquadrant.repository.ExamRepository;
import com.example.quizquadrant.service.*;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DraftExamServiceImpl implements DraftExamService {

    private final DraftExamRepository draftExamRepository;
    private final ValidationService validationService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<IdResponseDto> create(
            ExamDto examDto
    ) throws Exception {
//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        convert dto to json
        String data = "";
        try {
            data = objectMapper.writeValueAsString(examDto);
        } catch (Exception e) {
            throw new BadRequestError("Invalid data");
        }

//        save draft in database
        DraftExam draftExam = draftExamRepository.save(
                DraftExam
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
                                .id(draftExam.getId())
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> update(
            ExamDto examDto,
            String id
    ) throws Exception {
//        fetch draft-exam by id
        DraftExam draftExam = getDraftExamById(UUID.fromString(id));

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        authorize user permission on draft
        authorizeUserDraftExam(user, draftExam);

//        convert dto to json
        String data = "";
        try {
            data = objectMapper.writeValueAsString(examDto);
        } catch (Exception e) {
            throw new BadRequestError("Invalid data");
        }

//        update and save draft-exam in database
        draftExam.setData(data);
        draftExam.setLastModifiedOn(LocalDateTime.now());
        draftExamRepository.save(draftExam);

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
//        fetch draft-exam by id
        DraftExam draftExam = getDraftExamById(UUID.fromString(id));

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        authorize user permission on draft
        authorizeUserDraftExam(user, draftExam);

//        delete draft
        draftExamRepository.delete(draftExam);

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
    public ResponseEntity<List<ExamDto>> getMyDraftExams(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate pageSize
        validationService.validatePageSize(pageSize);

//        find authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        fetch draft-exams created by user
        List<DraftExam> draftExams = getDraftExamsByUser(user, pageNumber, pageSize);

//        create list of exam dto
        List<ExamDto> examDtos = new ArrayList<>();
        for (DraftExam draftExam : draftExams) {
            examDtos.add(
                    createExamDtoFromDraftExamData(draftExam)
            );
        }

//        if pageNumber is 0, calculate total number of questions created by user
//        and put it into first question
        if (pageNumber == 0) {
            int totalDrafts = draftExamRepository.countByCreatedBy(user);
            examDtos.add(0, ExamDto
                    .builder()
                    .createdBy(
                            UserDto
                                    .builder()
                                    .totalDraftExams(totalDrafts)
                                    .build()
                    )
                    .build()
            );
        }

//        response
        return ResponseEntity
                .status(200)
                .body(examDtos);
    }

    @Override
    public ResponseEntity<ExamDto> getDraftExamById(
            String id
    ) throws Exception {
//        fetch draft-exam by id
        DraftExam draftExam = getDraftExamById(UUID.fromString(id));

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        userService.authorizeUser(user);

//        authorize user permission on draft
        authorizeUserDraftExam(user, draftExam);

//        create dto
        ExamDto examDto = createExamDtoFromDraftExamData(draftExam);

//        response
        return ResponseEntity
                .status(200)
                .body(examDto);
    }

    @Override
    public DraftExam getDraftExamById(
            UUID id
    ) throws Exception {
        Optional<DraftExam> draftExamOptional = draftExamRepository.findById(id);
        if (draftExamOptional.isEmpty()) {
            throw new NotFoundError("Draft not found");
        }
        return draftExamOptional.get();
    }

    @Override
    public void authorizeUserDraftExam(
            User user,
            DraftExam draftExam
    ) throws Exception {
        if (!user.getId().equals(draftExam.getCreatedBy().getId())) {
            throw new AccessDeniedError();
        }
    }

    private ExamDto createExamDtoFromDraftExamData(
            DraftExam draftExam
    ) throws Exception {
        ExamDto examDto = objectMapper.readValue(draftExam.getData(), ExamDto.class);
        return ExamDto
                .builder()
                .id(examDto.id())
                .title(examDto.title())
                .startDateTime(examDto.startDateTime())
                .durationInMinutes(examDto.durationInMinutes())
                .isResultGenerated(examDto.isResultGenerated())
                .totalMarks(examDto.totalMarks())
                .lastModifiedOn(draftExam.getLastModifiedOn())
                .createdBy(examDto.createdBy())
                .questions(examDto.questions())
                .candidates(examDto.candidates())
                .build();
    }

    private List<DraftExam> getDraftExamsByUser(
            User user,
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<DraftExam> draftExamPage = draftExamRepository.findByCreatedBy(user, pageable);
        return draftExamPage.getContent();
    }

}

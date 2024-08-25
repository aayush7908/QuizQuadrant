package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.OptionDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.repository.QuestionRepository;
import com.example.quizquadrant.repository.SubjectRepository;
import com.example.quizquadrant.service.*;
import com.example.quizquadrant.utils.error.DuplicateDataError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.error.UnauthorizedAccessError;
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
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ValidationService validationService;

    @Override
    public ResponseEntity<BooleanResponseDto> create(
            SubjectDto subjectDto
    ) throws Exception {
//        validate input data
        validationService.validateCreateSubjectInput(subjectDto);

//        extract subject name in uppercase
        String subjectName = subjectDto.name().toUpperCase();

//        check if subject name already exists
        checkSubjectExists(subjectName);

//        save subject in database
        subjectRepository.save(
                Subject
                        .builder()
                        .name(subjectName)
                        .build()
        );

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
            SubjectDto subjectDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateSubjectInput(subjectDto);

//        extract subject name in uppercase
        String subjectName = subjectDto.name().toUpperCase();

//        check if subject name already exists
        checkSubjectExists(subjectName);

//        fetch subject by id
        Subject subject = getSubjectById(subjectDto.id());

//        update subject in database
        subject.setName(subjectName);
        subjectRepository.save(subject);

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
//        fetch subject by id
        Subject subject = getSubjectById(UUID.fromString(id));

//        delete subject from database
        subjectRepository.delete(subject);

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
    public Subject getSubjectById(
            UUID id
    ) throws Exception {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if (subjectOptional.isEmpty()) {
            throw new NotFoundError("Subject not found");
        }
        return subjectOptional.get();
    }

    private void checkSubjectExists(
            String name
    ) throws Exception {
        boolean isSubjectPresent = subjectRepository.existsByName(name);
        if (isSubjectPresent) {
            throw new DuplicateDataError("Subject already exists");
        }
    }

}

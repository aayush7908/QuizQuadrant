package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.repository.QuestionRepository;
import com.example.quizquadrant.repository.SubjectRepository;
import com.example.quizquadrant.repository.SubtopicRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubtopicRepository subtopicRepository;
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
            SubjectDto subjectDto,
            String id
    ) throws Exception {
//        validate input data
        validationService.validateUpdateSubjectInput(subjectDto);

//        extract subject name in uppercase
        String subjectName = subjectDto.name().toUpperCase();

//        check if subject name already exists
        checkSubjectExists(subjectName);

//        fetch subject by id
        Subject subject = getSubjectById(UUID.fromString(id));

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
    public ResponseEntity<List<SubjectDto>> getAll() throws Exception {
//        fetch all subjects
        List<Subject> subjects = subjectRepository.findAll();

//        create subject dto list
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for (Subject subject : subjects) {
//            fetch subtopics corresponding to a subject
            List<Subtopic> subtopics = subtopicRepository.findBySubject(subject);

//            create subtopic dto list
            List<SubtopicDto> subtopicDtos = new ArrayList<>();
            for (Subtopic subtopic : subtopics) {
//                create subtopic dto and add it to subtopic dto list
                subtopicDtos.add(
                        SubtopicDto
                                .builder()
                                .id(subtopic.getId())
                                .name(subtopic.getName())
                                .build()
                );
            }

//            create subject dto and add it to subject dto list
            subjectDtos.add(
                    SubjectDto
                            .builder()
                            .id(subject.getId())
                            .name(subject.getName())
                            .subtopics(subtopicDtos)
                            .build()
            );
        }

//        response
        return ResponseEntity
                .status(200)
                .body(subjectDtos);
    }

    @Override
    public ResponseEntity<SubjectDto> getById(
            String id
    ) throws Exception {
//        fetch subject by id
        Subject subject = getSubjectById(UUID.fromString(id));

//        response
        return ResponseEntity
                .status(200)
                .body(
                        SubjectDto
                                .builder()
                                .id(subject.getId())
                                .name(subject.getName())
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

package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.dto.SubtopicDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.repository.QuestionRepository;
import com.example.quizquadrant.repository.SubtopicRepository;
import com.example.quizquadrant.service.QuestionService;
import com.example.quizquadrant.service.SubjectService;
import com.example.quizquadrant.service.SubtopicService;
import com.example.quizquadrant.utils.error.DuplicateDataError;
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
public class SubtopicServiceImpl implements SubtopicService {

    private final SubtopicRepository subtopicRepository;
    private final SubjectService subjectService;
    private final ValidationService validationService;

    @Override
    public ResponseEntity<BooleanResponseDto> create(
            SubtopicDto subtopicDto
    ) throws Exception {
//        validate input data
        validationService.validateCreateSubtopicInput(subtopicDto);

//        extract subtopic name in uppercase
        String subtopicName = subtopicDto.name().toUpperCase();

//        fetch subject by id
        Subject subject = subjectService.getSubjectById(subtopicDto.subject().id());

//        check if subtopic exists
        checkSubtopicExists(subtopicName, subject);

//        save subtopic in database
        subtopicRepository.save(
                Subtopic
                        .builder()
                        .name(subtopicName)
                        .subject(subject)
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
            SubtopicDto subtopicDto,
            String id
    ) throws Exception {
//        validate input data
        validationService.validateUpdateSubtopicInput(subtopicDto);

//        extract subtopic name in uppercase
        String subtopicName = subtopicDto.name().toUpperCase();

//        fetch subtopic by id
        Subtopic subtopic = getSubtopicById(UUID.fromString(id));

//        fetch subject by id
        Subject subject = subjectService.getSubjectById(subtopicDto.subject().id());

//        check if subtopic exists
        checkSubtopicExists(subtopicName, subject);

//        update subtopic in database
        subtopic.setName(subtopicName);
        subtopic.setSubject(subject);
        subtopicRepository.save(subtopic);

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
//        fetch subtopic by id
        Subtopic subtopic = getSubtopicById(UUID.fromString(id));

//        delete subtopic from database
        subtopicRepository.delete(subtopic);

//        return
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
    public ResponseEntity<SubtopicDto> getById(
            String id
    ) throws Exception {
//        fetch subtopic by id
        Subtopic subtopic = getSubtopicById(UUID.fromString(id));

//        response
        return ResponseEntity
                .status(200)
                .body(
                        SubtopicDto
                                .builder()
                                .id(subtopic.getId())
                                .name(subtopic.getName())
                                .subject(
                                        SubjectDto
                                                .builder()
                                                .id(subtopic.getSubject().getId())
                                                .name(subtopic.getSubject().getName())
                                                .build()
                                )
                                .build()
                );
    }

    @Override
    public Subtopic getSubtopicById(
            UUID id
    ) throws Exception {
        Optional<Subtopic> subtopicOptional = subtopicRepository.findById(id);
        if (subtopicOptional.isEmpty()) {
            throw new NotFoundError("Subtopic not found");
        }
        return subtopicOptional.get();
    }

    @Override
    public List<Subtopic> getSubtopicsBySubject(
            Subject subject
    ) throws Exception {
        return subtopicRepository.findBySubject(subject);
    }

    private void checkSubtopicExists(
            String name,
            Subject subject
    ) throws Exception {
        boolean isSubtopicPresent = subtopicRepository.existsByNameAndSubject(name, subject);
        if (isSubtopicPresent) {
            throw new DuplicateDataError("Subtopic already exists");
        }
    }

}

package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.subtopic.SubtopicDto;
import com.example.quizquadrant.dto.subtopic.SubtopicRequestDto;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.SubtopicDtoMapper;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.repository.SubtopicRepository;
import com.example.quizquadrant.service.SubjectService;
import com.example.quizquadrant.service.SubtopicService;
import com.example.quizquadrant.utils.error.DuplicateDataError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubtopicServiceImpl implements SubtopicService {

    private final SubtopicRepository subtopicRepository;
    private final SubjectService subjectService;
    private final ValidationService validationService;
    private final SubtopicDtoMapper subtopicDtoMapper;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;

    @Override
    public ResponseEntity<BooleanResponseDto> create(
            SubtopicRequestDto subtopicRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateSubtopicRequestInput(subtopicRequestDto);

//        extract subtopic name in uppercase
        String subtopicName = subtopicRequestDto.name().toUpperCase();

//        fetch subject by id
        Subject subject = subjectService.getSubjectById(
                subtopicRequestDto.subjectId()
        );

//        check if subtopic exists
        checkSubtopicExists(subtopicName, subject);

//        save subtopic in database
        Subtopic subtopic = createSubtopic(
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
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> update(
            SubtopicRequestDto subtopicRequestDto,
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        validate input data
        validationService.validateSubtopicRequestInput(subtopicRequestDto);

//        extract subtopic name in uppercase
        String subtopicName = subtopicRequestDto.name().toUpperCase();

//        fetch subtopic by id
        Subtopic subtopic = getSubtopicById(uuid);

//        fetch subject by id
        Subject subject = subjectService.getSubjectById(
                subtopicRequestDto.subjectId()
        );

//        check if subtopic exists
        checkSubtopicExists(subtopicName, subject);

//        update subtopic in database
        subtopic.setName(subtopicName);
        subtopic.setSubject(subject);
        subtopic = updateSubtopic(subtopic);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch subtopic by id
        Subtopic subtopic = getSubtopicById(uuid);

//        delete subtopic from database
        deleteSubtopic(uuid);

//        return
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<SubtopicDto> getSubtopicById(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch subtopic by id
        Subtopic subtopic = getSubtopicById(uuid);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        subtopicDtoMapper
                                .toDto(subtopic, true)
                );
    }

    //    repository access methods

    @Override
    public Subtopic createSubtopic(Subtopic subtopic) {
        return subtopicRepository.save(subtopic);
    }

    @Override
    public Subtopic updateSubtopic(Subtopic subtopic) {
        return subtopicRepository.save(subtopic);
    }

    @Override
    public void deleteSubtopic(UUID id) {
        subtopicRepository.deleteById(id);
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

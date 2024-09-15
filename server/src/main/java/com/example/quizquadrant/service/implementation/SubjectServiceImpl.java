package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.SubjectDtoMapper;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.repository.SubjectRepository;
import com.example.quizquadrant.service.*;
import com.example.quizquadrant.utils.error.DuplicateDataError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ValidationService validationService;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;
    private final SubjectDtoMapper subjectDtoMapper;

    @Override
    public ResponseEntity<BooleanResponseDto> create(
            SubjectRequestDto subjectRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateSubjectRequestInput(subjectRequestDto);

//        extract subject name in uppercase
        String subjectName = subjectRequestDto.name().toUpperCase();

//        check if subject name already exists
        checkSubjectExists(subjectName);

//        save subject in database
        Subject subject = createSubject(
                Subject
                        .builder()
                        .name(subjectName)
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
            SubjectRequestDto subjectRequestDto,
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        validate input data
        validationService.validateSubjectRequestInput(subjectRequestDto);

//        extract subject name in uppercase
        String subjectName = subjectRequestDto.name().toUpperCase();

//        check if subject name already exists
        checkSubjectExists(subjectName);

//        fetch subject by id
        Subject subject = getSubjectById(uuid);

//        update subject in database
        subject.setName(subjectName);
        subject = updateSubject(subject);

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

//        fetch subject by id
        getSubjectById(uuid);

//        delete subject from database
        deleteSubject(uuid);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper.toDto(true)
                );
    }

    @Override
    public ResponseEntity<List<SubjectDto>> getAll() throws Exception {
//        fetch all subjects
        List<Subject> subjects = getAllSubjects();

//        create subject dto list
        List<SubjectDto> subjectDtos = subjectDtoMapper.toDtos(subjects);

//        response
        return ResponseEntity
                .status(200)
                .body(subjectDtos);
    }

    @Override
    public ResponseEntity<SubjectDto> getSubjectById(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch subject by id
        Subject subject = getSubjectById(uuid);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        subjectDtoMapper.toDto(subject)
                );
    }


    //    repository access methods
    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public void deleteSubject(UUID id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(
            UUID id
    ) throws Exception {
        Optional<Subject> subjectOptional = subjectRepository.findOnlySubjectById(id);
        if (subjectOptional.isEmpty()) {
            throw new NotFoundError("Subject Not Found");
        }
        return subjectOptional.get();
    }

    private void checkSubjectExists(
            String name
    ) throws Exception {
        boolean isSubjectPresent = subjectRepository.existsByName(name);
        if (isSubjectPresent) {
            throw new DuplicateDataError("Subject Already Exists");
        }
    }

}

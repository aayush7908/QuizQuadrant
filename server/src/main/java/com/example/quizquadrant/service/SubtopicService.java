package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.subtopic.SubtopicDto;
import com.example.quizquadrant.dto.subtopic.SubtopicRequestDto;
import com.example.quizquadrant.model.Subtopic;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SubtopicService {

    //    controller service methods
    ResponseEntity<BooleanResponseDto> create(
            SubtopicRequestDto subtopicRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> update(
            SubtopicRequestDto subtopicRequestDto,
            String id
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception;

    ResponseEntity<SubtopicDto> getSubtopicById(
            String id
    ) throws Exception;


    //    repository access methods
    Subtopic createSubtopic(Subtopic subtopic);

    Subtopic updateSubtopic(Subtopic subtopic);

    void deleteSubtopic(UUID id);

    Subtopic getSubtopicById(
            UUID id
    ) throws Exception;

}

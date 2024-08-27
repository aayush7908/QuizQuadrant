package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SubtopicService {

    //    controller service methods
    ResponseEntity<BooleanResponseDto> create(
            SubtopicDto subtopicDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> update(
            SubtopicDto subtopicDto,
            String id
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> delete(
            String id
    ) throws Exception;

    ResponseEntity<SubtopicDto> getById(
            String id
    ) throws Exception;

    //    helper methods
    Subtopic getSubtopicById(
            UUID id
    ) throws Exception;

    List<Subtopic> getSubtopicsBySubject(
            Subject subject
    ) throws Exception;

}

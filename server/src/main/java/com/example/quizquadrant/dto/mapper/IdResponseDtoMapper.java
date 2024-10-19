package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class IdResponseDtoMapper {

    public IdResponseDto toDto(Question question) {
        return IdResponseDto
                .builder()
                .id(question.getId())
                .build();
    }

}

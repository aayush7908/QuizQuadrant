package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.AuthenticationResponseDto;
import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.model.User;
import org.springframework.stereotype.Component;

@Component
public class BooleanResponseDtoMapper {

    public BooleanResponseDto toDto(
            boolean success
    ) {
        return BooleanResponseDto
                .builder()
                .success(success)
                .build();
    }

}

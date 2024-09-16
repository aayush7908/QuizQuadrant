package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.OptionDto;
import com.example.quizquadrant.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptionDtoMapper {

    public OptionDto toDto(
            Option option
    ) {
        return OptionDto
                .builder()
                .id(option.getId())
                .statement(option.getStatement())
                .imageUrl(option.getImageUrl())
                .isCorrect(option.getIsCorrect())
                .build();
    }

}

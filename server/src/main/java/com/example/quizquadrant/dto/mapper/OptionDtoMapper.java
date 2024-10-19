package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.question.OptionDto;
import com.example.quizquadrant.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<OptionDto> toDtos(
            List<Option> options
    ) {
        List<OptionDto> optionDtos = new ArrayList<>();
        for (Option option : options) {
            optionDtos.add(
                    toDto(option)
            );
        }
        return optionDtos;
    }

}

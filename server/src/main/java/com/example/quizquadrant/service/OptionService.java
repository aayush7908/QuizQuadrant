package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.OptionRequestDto;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;

import java.util.List;
import java.util.UUID;

public interface OptionService {

    //    controller service methods
    Option create(
            OptionRequestDto optionRequestDto,
            Question question
    ) throws Exception;

    List<Option> create(
            List<OptionRequestDto> optionRequestDtos,
            Question question
    ) throws Exception;

    Option update(
            OptionRequestDto optionRequestDto,
            Option option
    ) throws Exception;

    List<Option> update(
            List<OptionRequestDto> optionRequestDtos,
            Question question
    ) throws Exception;

    //    repository access methods
    Option createOption(
            Option option
    );
    Option updateOption(
            Option option
    ) throws Exception;

    List<Option> getOptionsByQuestion(
            Question question
    ) throws Exception;

    Option getOptionById(
            UUID id
    ) throws Exception;

}

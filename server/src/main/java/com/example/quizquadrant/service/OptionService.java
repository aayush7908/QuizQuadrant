package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.OptionDto;
import com.example.quizquadrant.dto.SolutionDto;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;

import java.util.UUID;

public interface OptionService {

    //    helper methods
    void create(
            OptionDto optionDto,
            Question question
    ) throws Exception;

    void update(
            OptionDto optionDto
    ) throws Exception;

    Option getOptionById(
            UUID id
    ) throws Exception;

}

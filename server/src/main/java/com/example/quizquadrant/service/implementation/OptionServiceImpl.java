package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.OptionDto;
import com.example.quizquadrant.dto.SolutionDto;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;
import com.example.quizquadrant.repository.OptionRepository;
import com.example.quizquadrant.repository.SolutionRepository;
import com.example.quizquadrant.service.OptionService;
import com.example.quizquadrant.service.SolutionService;
import com.example.quizquadrant.utils.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    @Override
    public void create(
            OptionDto optionDto,
            Question question
    ) throws Exception {
        optionRepository.save(
                Option
                        .builder()
                        .statement(optionDto.statement())
                        .imageUrl(optionDto.imageUrl())
                        .isCorrect(optionDto.isCorrect())
                        .question(question)
                        .build()
        );
    }

    @Override
    public void update(
            OptionDto optionDto
    ) throws Exception {
//        fetch option by id
        Option option = getOptionById(optionDto.id());

//        update option in database
        option.setStatement(optionDto.statement());
        option.setImageUrl(optionDto.imageUrl());
        option.setIsCorrect(optionDto.isCorrect());
        optionRepository.save(option);
    }

    @Override
    public Option getOptionById(
            UUID id
    ) throws Exception {
        Optional<Option> optionOptional = optionRepository.findById(id);
        if (optionOptional.isEmpty()) {
            throw new NotFoundError("Option not found");
        }
        return optionOptional.get();
    }

}

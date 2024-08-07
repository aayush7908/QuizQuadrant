package com.example.quizquadrant.service;

import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Option createOption(
            String statement,
            Boolean hasImage,
            Boolean isCorrect,
            Question question
    ) {
        Option option = new Option(
                statement,
                hasImage,
                isCorrect,
                question
        );
        return optionRepository.save(option);
    }
}

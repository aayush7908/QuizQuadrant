package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.question.OptionRequestDto;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.repository.OptionRepository;
import com.example.quizquadrant.service.OptionService;
import com.example.quizquadrant.utils.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    //    controller service methods
    @Override
    public Option create(
            OptionRequestDto optionRequestDto,
            Question question
    ) throws Exception {
//        create option
        Option option = Option
                .builder()
                .statement(optionRequestDto.statement())
                .imageUrl(optionRequestDto.imageUrl())
                .isCorrect(optionRequestDto.isCorrect())
                .question(question)
                .build();

//        save in database
        return createOption(option);
    }

    @Override
    public List<Option> create(
            List<OptionRequestDto> optionRequestDtos,
            Question question
    ) throws Exception {
        List<Option> options = new ArrayList<>();
        for (OptionRequestDto optionRequestDto : optionRequestDtos) {
            options.add(
                    create(optionRequestDto, question)
            );
        }
        return options;
    }

    @Override
    public Option update(
            OptionRequestDto optionRequestDto,
            Option option
    ) throws Exception {
//        update option
        option.setStatement(optionRequestDto.statement());
        option.setImageUrl(optionRequestDto.imageUrl());
        option.setIsCorrect(optionRequestDto.isCorrect());

//        save in database
        return updateOption(option);
    }

    @Override
    public List<Option> update(
            List<OptionRequestDto> optionRequestDtos,
            Question question
    ) throws Exception {
//        fetch options by question
        List<Option> options = getOptionsByQuestion(question);

//        update each option
        for (int i = 0; i < options.size(); i++) {
            options.set(i, update(
                    optionRequestDtos.get(i),
                    options.get(i))
            );
        }

        return options;
    }

    //    repository access methods
    @Override
    public Option createOption(
            Option option
    ) {
        return optionRepository.save(option);
    }

    @Override
    public Option updateOption(
            Option option
    ) {
        return optionRepository.save(option);
    }

    @Override
    public List<Option> getOptionsByQuestion(
            Question question
    ) throws Exception {
        Optional<List<Option>> optionOptional = optionRepository.findByQuestion(question);
        if (optionOptional.isEmpty()) {
            throw new NotFoundError("Option Not Found");
        }
        return optionOptional.get();
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

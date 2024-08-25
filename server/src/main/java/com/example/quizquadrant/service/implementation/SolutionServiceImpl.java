package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.BooleanResponseDto;
import com.example.quizquadrant.dto.QuestionDto;
import com.example.quizquadrant.dto.SolutionDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.repository.QuestionRepository;
import com.example.quizquadrant.repository.SolutionRepository;
import com.example.quizquadrant.service.QuestionService;
import com.example.quizquadrant.service.SolutionService;
import com.example.quizquadrant.service.SubtopicService;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;

    @Override
    public void create(
            SolutionDto solutionDto,
            Question question
    ) throws Exception {
        solutionRepository.save(
                Solution
                        .builder()
                        .statement(solutionDto.statement())
                        .imageUrl(solutionDto.imageUrl())
                        .question(question)
                        .build()
        );
    }

    @Override
    public void update(
            SolutionDto solutionDto
    ) throws Exception {
//        fetch solution by id
        Solution solution = getSolutionById(solutionDto.id());

//        update solution in database
        solution.setStatement(solutionDto.statement());
        solution.setImageUrl(solutionDto.imageUrl());
        solutionRepository.save(solution);
    }

    @Override
    public Solution getSolutionById(
            UUID id
    ) throws Exception {
        Optional<Solution> solutionOptional = solutionRepository.findById(id);
        if (solutionOptional.isEmpty()) {
            throw new NotFoundError("Solution not found");
        }
        return solutionOptional.get();
    }

}

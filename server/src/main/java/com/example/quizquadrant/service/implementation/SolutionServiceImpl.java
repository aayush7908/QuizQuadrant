package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.question.SolutionRequestDto;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Solution;
import com.example.quizquadrant.repository.SolutionRepository;
import com.example.quizquadrant.service.SolutionService;
import com.example.quizquadrant.utils.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;

    //    controller service methods
    @Override
    public Solution create(
            SolutionRequestDto solutionRequestDto,
            Question question
    ) {
//        create solution
        Solution solution = Solution
                .builder()
                .statement(solutionRequestDto.statement())
                .imageUrl(solutionRequestDto.imageUrl())
                .question(question)
                .build();

//        save in database
        return createSolution(solution);
    }

    @Override
    public Solution update(
            SolutionRequestDto solutionRequestDto,
            Question question
    ) throws Exception {
//        fetch solution by question
        Solution solution = getSolutionByQuestion(question);

//        update solution
        solution.setStatement(solutionRequestDto.statement());
        solution.setImageUrl(solutionRequestDto.imageUrl());

//        save in database
        return updateSolution(solution);
    }

    //    repository access methods
    @Override
    public Solution createSolution(
            Solution solution
    ) {
        return solutionRepository.save(solution);
    }

    @Override
    public Solution updateSolution(
            Solution solution
    ) {
        return solutionRepository.save(solution);
    }

    @Override
    public Solution getSolutionByQuestion(Question question) throws Exception {
        Optional<Solution> solutionOptional = solutionRepository.findByQuestion(question);
        if (solutionOptional.isEmpty()) {
            throw new NotFoundError("Solution not found");
        }
        return solutionOptional.get();
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

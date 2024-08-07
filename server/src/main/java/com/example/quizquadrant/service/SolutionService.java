package com.example.quizquadrant.service;

import com.example.quizquadrant.model.Solution;
import com.example.quizquadrant.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;
    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public Solution createSolution(Boolean hasImage, String statement) {
        Solution s = new Solution(statement, hasImage);
        return solutionRepository.save(s);
    }
}

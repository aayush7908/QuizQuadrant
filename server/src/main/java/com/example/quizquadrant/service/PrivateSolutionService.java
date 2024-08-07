package com.example.quizquadrant.service;

import com.example.quizquadrant.model.PrivateSolution;
import com.example.quizquadrant.repository.PrivateSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivateSolutionService {

    private final PrivateSolutionRepository privateSolutionRepository;

    @Autowired
    public PrivateSolutionService(PrivateSolutionRepository privateSolutionRepository) {
        this.privateSolutionRepository = privateSolutionRepository;
    }

    public PrivateSolution createPrivateSolution(Boolean hasImage, String statement) {
        PrivateSolution privateSolution = new PrivateSolution(statement, hasImage);
        return privateSolutionRepository.save(privateSolution);
    }

    public void removePrivateSolution(PrivateSolution privateSolution) {
        privateSolutionRepository.deletePrivateSolution(privateSolution);
    }

}

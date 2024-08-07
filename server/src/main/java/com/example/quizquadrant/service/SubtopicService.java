package com.example.quizquadrant.service;

import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.model.SubtopicKey;
import com.example.quizquadrant.repository.SubtopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubtopicService {

    private final SubtopicRepository subtopicRepository;
    private final SubjectService subjectService;

    @Autowired
    public SubtopicService(SubtopicRepository subtopicRepository, SubjectService subjectService) {
        this.subtopicRepository = subtopicRepository;
        this.subjectService = subjectService;
    }

    public Subtopic getSubtopicById(Long subjectId, Long subtopicId) {
        Subject subject = subjectService.getSubjectById(subjectId);
        if(subject != null) {
            SubtopicKey subtopicKey = new SubtopicKey();
            subtopicKey.setId(subtopicId);
            subtopicKey.setSubject(subject);
            Optional<Subtopic> subtopicOptional = subtopicRepository.findById(subtopicKey);
            return subtopicOptional.orElse(null);
        } else {
            return null;
        }
    }
}

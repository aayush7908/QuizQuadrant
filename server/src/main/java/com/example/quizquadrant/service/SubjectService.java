package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.dto.SubtopicDto;
import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject getSubjectById(Long subjectId) {
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        return subjectOptional.orElse(null);
    }

    public List<SubjectDto> getAllSubjects() {

        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for (Subject subject : subjects) {
            Integer toq = 0;
            List<SubtopicDto> subtopicDtos = new ArrayList<>();
            for(Subtopic subtopic : subject.getSubtopics()) {
                SubtopicDto subtopicDto = new SubtopicDto(
                        subtopic.getSubtopicName(),
                        subject.getId(),
                        subtopic.getQuestions().size(),
                        subtopic.getId()
                );
                toq += subtopic.getQuestions().size();
                subtopicDtos.add(subtopicDto);
            }

            SubjectDto subjectDto = new SubjectDto(subject.getSubName(), subject.getId(),toq, subtopicDtos);
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }


}

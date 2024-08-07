package com.example.quizquadrant.service;


import com.example.quizquadrant.dto.PrivateQuestionResponsesDto;
import com.example.quizquadrant.model.*;
import com.example.quizquadrant.repository.ExamResponsesRepository;
import com.example.quizquadrant.repository.PrivateQuestionRepository;
import com.example.quizquadrant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamResponsesService {

    private final ExamResponsesRepository examResponsesRepository;
    private final UserRepository userRepository;
    private final PrivateQuestionRepository privateQuestionRepository;
    private  final PrivateOptionService privateOptionService;

    @Autowired
    public ExamResponsesService(ExamResponsesRepository examResponsesRepository, UserRepository userRepository, PrivateQuestionRepository privateQuestionRepository, PrivateOptionService privateOptionService) {
        this.examResponsesRepository = examResponsesRepository;
        this.userRepository = userRepository;
        this.privateQuestionRepository = privateQuestionRepository;
        this.privateOptionService = privateOptionService;
    }

    public ExamResponses storePrivateQuestionResponses(Long userId, Long privateQuestionId, PrivateQuestionResponsesDto privateQuestionResponsesDto) {

        User u = userRepository.findById(userId).get();
        PrivateQuestion q = privateQuestionRepository.findById(privateQuestionId).get();

        privateOptionService.sortPrivateOptions(q.getPrivateOptions());

        ExamResponseKey examResponseKey = new ExamResponseKey();
        examResponseKey.setUser(u);
        examResponseKey.setPrivateQuestion(q);

        List<Boolean> optionsMarked = new ArrayList<>();

        for(PrivateOption privateOption: q.getPrivateOptions()) {
            if(privateQuestionResponsesDto.responses().contains(privateOption.getId())) {
                optionsMarked.add(true);
            } else {
                optionsMarked.add(false);
            }
        }

        if(optionsMarked.get(0) || optionsMarked.get(1) || optionsMarked.get(2) || optionsMarked.get(3)) {
            Optional<ExamResponses> examResponsesOptional = examResponsesRepository.findById(examResponseKey);
            if (examResponsesOptional.isPresent()) {
//            ExamResponses examResponses = examResponsesOptional.get();
//            examResponses.setOptionAMarked(optionsMarked.get(0));
//            examResponses.setOptionBMarked(optionsMarked.get(1));
//            examResponses.setOptionCMarked(optionsMarked.get(2));
//            examResponses.setOptionDMarked(optionsMarked.get(3));
                examResponsesRepository.updateExamResponsesByUserAndPrivateQuestion(u, q, optionsMarked.get(0), optionsMarked.get(1), optionsMarked.get(2), optionsMarked.get(3));
                return examResponsesRepository.findExamResponsesByUserAndPrivateQuestion(u, q);
            } else {
                ExamResponses examResponsesNew = new ExamResponses(
                        u,
                        q,
                        optionsMarked.get(0),
                        optionsMarked.get(1),
                        optionsMarked.get(2),
                        optionsMarked.get(3)
                );
                return examResponsesRepository.save(examResponsesNew);
            }
        }
        else {
            examResponsesRepository.deleteExamResponsesByUserAndPrivateQuestion(u,q);
            return null;
        }
    }

    public ExamResponses getExamResponsesByUserAndQuestion(User user, PrivateQuestion privateQuestion) {
        return examResponsesRepository.findExamResponsesByUserAndPrivateQuestion(user, privateQuestion);
    }

    public void removeResponses(User user, List<PrivateQuestion> privateQuestionList) {
        examResponsesRepository.deleteExamResponsesByUserAndPrivateQuestions(user, privateQuestionList);
    }
}
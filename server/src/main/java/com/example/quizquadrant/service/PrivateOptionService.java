package com.example.quizquadrant.service;

import com.example.quizquadrant.model.PrivateOption;
import com.example.quizquadrant.model.PrivateQuestion;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.repository.PrivateOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PrivateOptionService {

    private final PrivateOptionRepository privateOptionRepository;

    @Autowired
    public PrivateOptionService(PrivateOptionRepository privateOptionRepository) {
        this.privateOptionRepository = privateOptionRepository;
    }

    public PrivateOption createPrivateOption(
            String statement,
            Boolean hasImage,
            Boolean isCorrect,
            PrivateQuestion privateQuestion
    ) {
        PrivateOption privateOption = new PrivateOption(
                statement,
                hasImage,
                isCorrect,
                privateQuestion
        );
        return privateOptionRepository.save(privateOption);
    }

    public void removePrivateOptions(List<PrivateOption> privateOptions) {
        privateOptionRepository.deletePrivateOptions(privateOptions);
    }

    public void sortPrivateOptions(List<PrivateOption> privateOptions) {
        Collections.sort(privateOptions, new PrivateOptionComparator());
    }

    class PrivateOptionComparator implements Comparator<PrivateOption> {
        public int compare(PrivateOption privateOption1, PrivateOption privateOption2) {
            return privateOption1.getId().compareTo(privateOption2.getId());
        }
    }
}

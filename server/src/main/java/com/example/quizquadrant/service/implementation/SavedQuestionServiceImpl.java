package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.SavedQuestion;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.repository.SavedQuestionRepository;
import com.example.quizquadrant.service.SavedQuestionService;
import com.example.quizquadrant.utils.error.DuplicateDataError;
import com.example.quizquadrant.utils.error.NotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SavedQuestionServiceImpl implements SavedQuestionService {

    private final SavedQuestionRepository savedQuestionRepository;

    //    repository access methods
    @Override
    public SavedQuestion createSavedQuestion(SavedQuestion savedQuestion) {
        return savedQuestionRepository.save(savedQuestion);
    }

    @Override
    public void deleteSavedQuestion(SavedQuestion savedQuestion) {
        savedQuestionRepository.delete(savedQuestion);
    }

    @Override
    public SavedQuestion getSavedQuestion(
            User user,
            Question question
    ) throws Exception {
        Optional<SavedQuestion> savedQuestionOptional = savedQuestionRepository.findByUserAndQuestion(user, question);
        if (savedQuestionOptional.isEmpty()) {
            throw new NotFoundError("Saved Question Not Found");
        }
        return savedQuestionOptional.get();
    }

    @Override
    public List<SavedQuestion> getSavedQuestionsByUser(
            User user,
            Integer pageNumber,
            Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SavedQuestion> savedQuestionPage = savedQuestionRepository.findByUser(user, pageable);
        return savedQuestionPage.getContent();
    }

    @Override
    public Boolean checkSavedQuestionExists(User user, Question question) {
        return savedQuestionRepository.existsByUserAndQuestion(user, question);
    }

    @Override
    public Integer countQuestionsSavedByUser(User user) {
        return savedQuestionRepository.countByUser(user);
    }

    //    helper methods
    @Override
    public SavedQuestion saveQuestion(
            User user,
            Question question
    ) throws Exception {
//        check if question is already saved
        boolean isQuestionSaved = checkSavedQuestionExists(user, question);
        if (isQuestionSaved) {
            throw new DuplicateDataError("Question Already Saved");
        }

//        create object for saved-question
        SavedQuestion savedQuestion = SavedQuestion
                .builder()
                .user(user)
                .question(question)
                .build();

//        save in database
        savedQuestion = createSavedQuestion(savedQuestion);

//        return
        return savedQuestion;
    }

    @Override
    public void unsaveQuestion(
            User user,
            Question question
    ) throws Exception {
//        fetch saved-question
        SavedQuestion savedQuestion = getSavedQuestion(user, question);

//        delete from database
        deleteSavedQuestion(savedQuestion);
    }
}

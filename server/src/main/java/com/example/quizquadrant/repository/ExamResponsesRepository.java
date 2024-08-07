package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.ExamResponseKey;
import com.example.quizquadrant.model.ExamResponses;
import com.example.quizquadrant.model.PrivateQuestion;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResponsesRepository extends JpaRepository<ExamResponses, ExamResponseKey> {

    public ExamResponses findExamResponsesByUserAndPrivateQuestion(User user, PrivateQuestion privateQuestion);

    @Transactional
    @Modifying
    @Query("UPDATE ExamResponses er SET er.optionAMarked = :optionAMarked, er.optionBMarked = :optionBMarked,er.optionCMarked = :optionCMarked,er.optionDMarked = :optionDMarked WHERE er.user = :user AND er.privateQuestion = :privateQuestion")
    public void updateExamResponsesByUserAndPrivateQuestion(User user, PrivateQuestion privateQuestion, Boolean optionAMarked, Boolean optionBMarked, Boolean optionCMarked, Boolean optionDMarked);

    @Transactional
    @Modifying
    @Query("DELETE FROM ExamResponses er WHERE er.user = :user AND er.privateQuestion =:privateQuestion")
    public void deleteExamResponsesByUserAndPrivateQuestion(User user, PrivateQuestion privateQuestion);


    @Transactional
    @Modifying
    @Query("DELETE FROM ExamResponses er WHERE er.user = :user AND er.privateQuestion in :privateQuestions")
    public void deleteExamResponsesByUserAndPrivateQuestions(User user, List<PrivateQuestion> privateQuestions);

}

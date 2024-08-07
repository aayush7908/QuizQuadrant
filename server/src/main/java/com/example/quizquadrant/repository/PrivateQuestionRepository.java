package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.PrivateQuestion;
import com.example.quizquadrant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PrivateQuestionRepository extends JpaRepository<PrivateQuestion, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PrivateQuestion pq where pq in :privateQuestion")
    public void deletePrivateQuestion(PrivateQuestion privateQuestion);

    @Transactional

    @Modifying
    @Query("DELETE FROM PrivateQuestion pq where pq in :privateQuestions")
    public void deletePrivateQuestions(List<PrivateQuestion> privateQuestions);

}

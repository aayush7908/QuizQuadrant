package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.ExamQuestion;
import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.key.ExamQuestionKey;
import com.example.quizquadrant.model.key.ExamResponseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, ExamQuestionKey> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM ExamQuestion e WHERE e.exam = :exam AND e.question = :question")
    Boolean existsByExamAndQuestion(Exam exam, Question question);

    @Transactional
    @Modifying
    @Query("DELETE FROM ExamQuestion e WHERE e.exam = :exam AND e.question = :question")
    void deleteByExamAndQuestion(Exam exam, Question question);

}

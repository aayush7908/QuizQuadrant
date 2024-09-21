package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.SavedQuestion;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.key.SavedQuestionKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SavedQuestionRepository extends JpaRepository<SavedQuestion, SavedQuestionKey> {

    @Query("SELECT sq FROM SavedQuestion sq WHERE sq.user = :user AND sq.question = :question")
    Optional<SavedQuestion> findByUserAndQuestion(User user, Question question);

    @Query("SELECT sq FROM SavedQuestion sq WHERE sq.user = :user")
    Optional<List<SavedQuestion>> findByUser(User user, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(sq) > 0 THEN TRUE ELSE false END FROM SavedQuestion sq WHERE sq.user = :user AND sq.question = :question")
    Boolean existsByUserAndQuestion(User user, Question question);

    @Query("SELECT COUNT(sq) FROM SavedQuestion sq WHERE sq.user = :user")
    Integer countByUser(User user);

}

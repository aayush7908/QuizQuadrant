package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.Result;
import com.example.quizquadrant.model.ResultKey;
import com.example.quizquadrant.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, ResultKey> {

    //List<Result> findTop10ByExamOrderByMarksDesc(Exam exam);

    List<Result> findTop10ResultsByIsPresentAndExamOrderByMarksDesc(Boolean isPresent, Exam exam);
  
    List<Result> findByExamOrderByMarksDesc(Exam exam);

    @Transactional
    @Modifying
    @Query("UPDATE Result r set r.isPresent=true where r.user = :user and r.exam = :exam")
    public void updateIsPresentResultByUserAndExam(User user, Exam exam);

    @Transactional
    @Modifying
    @Query("UPDATE Result r set r.isFinished=true where r.user = :user and r.exam = :exam")
    public void updateIsFinishedResultByUserAndExam(User user, Exam exam);

    @Transactional
    @Modifying
    @Query("UPDATE Result r SET r.marks = :marks WHERE r.user = :user AND r.exam = :exam")
    public void updateResultMarksByUserAndExam(User user, Exam exam, Integer marks);

    public Result findResultsByExamAndUser (Exam exam, User user);
}
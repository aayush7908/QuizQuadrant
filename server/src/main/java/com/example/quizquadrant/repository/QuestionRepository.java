package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.Subtopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.subtopic = :subtopic")
    public Page<Question> findQuestionsBySubtopic(Subtopic subtopic, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.subtopic in :subtopics")
    public Page<Question> findQuestionsBySubtopics(List<Subtopic> subtopics, Pageable pageable);

    @Query("SELECT q.id FROM Question q WHERE q.subtopic in :subtopics")
    public Optional<List<Long>> findQuestionIdsBySubtopics(List<Subtopic> subtopics, Pageable pageable);

    @Query("SELECT SUM(q.positiveMarks) from Question q where q.id in :questionIds")
    public Long getSumOfPositiveMarks(List<Long> questionIds);

//    @Query("SELECT COUNT(*) from Question q where q.subtopic in :subtopics")
//    public Long countAllBySubtopic(List<Subtopic> subtopics);


}

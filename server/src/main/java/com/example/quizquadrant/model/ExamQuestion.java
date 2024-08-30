package com.example.quizquadrant.model;

import com.example.quizquadrant.model.key.ExamQuestionKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_question")
@IdClass(ExamQuestionKey.class)
public class ExamQuestion {

    @Id
    @ManyToOne
    @JoinColumn(
            name = "exam_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_examquestion_exam")
    )
    @JsonBackReference
    private Exam exam;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_examquestion_question")
    )
    @JsonBackReference
    private Question question;

    @Column(
            name = "positive_marks",
            nullable = false
    )
    private Integer positiveMarks;

    @Column(
            name = "negative_marks",
            nullable = false
    )
    private Integer negativeMarks;

    @OneToMany(
            mappedBy = "examQuestion",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<ExamResponse> examResponses;

}
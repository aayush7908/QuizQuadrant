package com.example.quizquadrant.model;

import com.example.quizquadrant.model.key.ExamCandidateKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_exam_candidate")
@IdClass(ExamCandidateKey.class)
public class ExamCandidate {

    @Id
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_examcandidate_user")
    )
    @JsonBackReference
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "exam_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_examcandidate_exam")
    )
    @JsonBackReference
    private Exam exam;

    @Column(
            name = "is_present",
            nullable = false
    )
    private Boolean isPresent;

    @Column(
            name = "exam_finished_on",
            columnDefinition = "TIMESTAMP"
    )
    private Boolean examFinishedOn;

    @Column(
            name = "obtained_marks",
            columnDefinition = "INTEGER"
    )
    private Integer obtainedMarks;

}

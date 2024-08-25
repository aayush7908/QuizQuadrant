package com.example.quizquadrant.model;

import com.example.quizquadrant.model.key.ResultKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "result")
@IdClass(ResultKey.class)
public class Result {

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_result_user")
    )
    private User user;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(
            name = "exam_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_result_exam")
    )
    private Exam exam;

    @Column (
            name = "is_present",
            nullable = false
    )
    private Boolean isPresent;

    @Column (
            name = "is_finished",
            nullable = false
    )
    private Boolean isFinished;

    @Column(
            name = "obtained_marks",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private Integer obtainedMarks;

}

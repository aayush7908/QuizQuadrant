package com.example.quizquadrant.model;

import com.example.quizquadrant.model.key.ExamResponseKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_exam_response")
@IdClass(ExamResponseKey.class)
public class ExamResponse {

    @Id
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_examresponse_user")
    )
    @JsonBackReference
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "exam_id",
            referencedColumnName = "exam_id",
            foreignKey = @ForeignKey(name = "fk_examresponse_examquestion")
    )
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "question_id",
            foreignKey = @ForeignKey(name = "fk_examresponse_examquestion")
    )
    @JsonBackReference
    private ExamQuestion examQuestion;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "option_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_examresponse_option")
    )
    @JsonBackReference
    private Option option;

}

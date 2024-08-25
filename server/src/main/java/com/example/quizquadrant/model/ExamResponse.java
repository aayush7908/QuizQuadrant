package com.example.quizquadrant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exam_response")
@IdClass(ExamResponseKey.class)
@Data
@NoArgsConstructor
public class ExamResponses {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(
            name = "optionAMarked",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean optionAMarked;

    @Column(
            name = "optionBMarked",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean optionBMarked;

    @Column(
            name = "optionCMarked",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean optionCMarked;

    @Column(
            name = "optionDMarked",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean optionDMarked;
    
    
    
    
//     constructor

     public ExamResponses(User user, PrivateQuestion privateQuestion, Boolean optionAMarked, Boolean optionBMarked, Boolean optionCMarked, Boolean optionDMarked) {
        this.user = user;
        this.privateQuestion = privateQuestion;
        this.optionAMarked = optionAMarked;
        this.optionBMarked = optionBMarked;
        this.optionCMarked = optionCMarked;
        this.optionDMarked = optionDMarked;
    }

}

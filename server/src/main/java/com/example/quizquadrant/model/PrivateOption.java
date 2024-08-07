package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "privateOption")
public class PrivateOption {
    @Id
    @SequenceGenerator(
            name = "private_option_sequence",
            sequenceName = "private_option_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "private_option_sequence"
    )
    private Long id;

    @Column(
            name = "statement",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String statement;

    @Column(
            name = "hasImage",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean hasImage;

    @Column(
            name = "isCorrect",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean isCorrect;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(
            name = "privateQuestionId"
    )
    private PrivateQuestion privateQuestion;




//    constructor

    public PrivateOption(
            String statement,
            Boolean hasImage,
            Boolean isCorrect,
            PrivateQuestion privateQuestion
    ) {
        this.statement = statement;
        this.hasImage = hasImage;
        this.isCorrect = isCorrect;
        this.privateQuestion = privateQuestion;
    }
 
}

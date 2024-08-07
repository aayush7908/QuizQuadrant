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
@Table(name = "option_table")
public class Option {

    @Id
    @SequenceGenerator(
            name = "option_sequence",
            sequenceName = "option_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "option_sequence"
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

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "questionId")
    private Question question;




//    Constructor

    public Option(
            String statement,
            Boolean hasImage,
            Boolean isCorrect,
            Question question
    ) {
        this.statement = statement;
        this.hasImage = hasImage;
        this.isCorrect = isCorrect;
        this.question = question;
    }
}

package com.example.quizquadrant.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "question"

)
@ToString
public class Question implements Serializable {
    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    private Long id;

    @Column(
            name = "positiveMarks",
            nullable = false
    )
    private Integer positiveMarks;

    @Column(
            name = "negativeMarks",
            nullable = false
    )
    private Integer negativeMarks;


    @Column(
            name = "statement",
            nullable = false,
            columnDefinition ="LONGTEXT"
    )
    private String statement;

    @Column(
            name = "type",
            nullable = false,
            columnDefinition ="CHAR(3)"
    )
    private String type;


    @Column(
            name = "hasImage",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean hasImage;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "subtopicId", referencedColumnName = "id")
    @JoinColumn(name = "subjectId", referencedColumnName = "subjectId")
    private Subtopic subtopic;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Option> options;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "solutionId")
    private Solution solution;




//    constructor

    public Question(
            Integer positiveMarks,
            Integer negativeMarks,
            String statement,
            String type,
            Boolean hasImage,
            Subtopic subtopic,
            Solution solution
    ) {
        this.positiveMarks = positiveMarks;
        this.negativeMarks = negativeMarks;
        this.statement = statement;
        this.type = type;
        this.hasImage = hasImage;
        this.subtopic = subtopic;
        this.solution = solution;
    }
}

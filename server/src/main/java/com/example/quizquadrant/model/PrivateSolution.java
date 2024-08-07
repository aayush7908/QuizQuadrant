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
@Table(name = "privateSolution")
public class PrivateSolution {

    @Id
    @SequenceGenerator(
            name = "solution_sequence",
            sequenceName = "solution_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "solution_sequence"
    )
    private Long id;

    @Column(
            name = "statement",
            nullable = false,
            columnDefinition = "LONGTEXT"
    )
    private String statement;

    @Column(
            name = "hasImage",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean hasImage;

    @OneToOne(
            mappedBy = "privateSolution",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private PrivateQuestion privateQuestion;




//    constructor

    public PrivateSolution(String statement, Boolean hasImage) {
        this.statement = statement;
        this.hasImage = hasImage;
    }

}

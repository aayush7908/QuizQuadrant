package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solution")
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "statement",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String statement;

    @Column(
            name = "image_url",
            columnDefinition = "TINYTEXT"
    )
    private String imageUrl;

    @OneToOne
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_solution_question")
    )
    @JsonBackReference
    private Question question;

}

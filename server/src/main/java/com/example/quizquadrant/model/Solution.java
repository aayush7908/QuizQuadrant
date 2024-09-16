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
@Table(name = "_solution")
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
            columnDefinition = "TEXT"
    )
    private String imageUrl;

    @OneToOne
    @JoinColumn(
            name = "question_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_solution_question")
    )
    @JsonBackReference
    private Question question;

}

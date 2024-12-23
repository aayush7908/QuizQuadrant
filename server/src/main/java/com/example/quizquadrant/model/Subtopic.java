package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "_subtopic",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_subtopic_name",
                        columnNames = {"subject_id", "name"}
                )
        }
)
public class Subtopic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "subject_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_subtopic_subject")
    )
    @JsonBackReference
    private Subject subject;

    @OneToMany(
            mappedBy = "subtopic",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<Question> questions;

}

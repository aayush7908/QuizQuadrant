package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
        name = "_subject",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_subject_name",
                        columnNames = "name"
                )
        }
)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "name",
            unique = true,
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private String name;

    @OneToMany(
            mappedBy = "subject",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Subtopic> subtopics;

}

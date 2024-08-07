package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subtopic")
@IdClass(SubtopicKey.class)
public class Subtopic {
    @Id
    @SequenceGenerator(
            name = "subtopic_sequence",
            sequenceName = "subtopic_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subtopic_sequence"
    )
    private Long id;

    @Column(
            name = "subtopicName",
            nullable = false
         
    )
    private String subtopicName;

    @ManyToOne
    @JoinColumn(name = "subjectId")
    @Id
    private Subject subject;

    @OneToMany(
            mappedBy = "subtopic",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Question> questions;




//    constructors

    public Subtopic(String subtopicName, Subject subject) {
        this.subtopicName = subtopicName;
        this.subject = subject;
    }

}

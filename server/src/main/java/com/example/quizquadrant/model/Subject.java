package com.example.quizquadrant.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @SequenceGenerator(
            name = "subject_sequence",
            sequenceName = "subject_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subject_sequence"
    )
    private Long id;

    @Column(
            name = "subName",
            nullable = false
    )
    private String subName;

    @OneToMany(
            mappedBy = "subject",
            cascade = CascadeType.REMOVE
    )
    private List<Subtopic> subtopics;




//    constructors ...

    public Subject(String subName) {
        this.subName = subName;
    }

}

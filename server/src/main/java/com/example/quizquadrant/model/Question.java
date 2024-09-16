package com.example.quizquadrant.model;


import com.example.quizquadrant.model.type.QuestionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "type",
            nullable = false,
            columnDefinition = "VARCHAR(3)"
    )
    private QuestionType type;

    @Column(
            name = "is_public",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean isPublic;

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

    @Column(
            name = "last_modified_on",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime lastModifiedOn;

    @ManyToOne
    @JoinColumn(
            name = "subtopic_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_question_subtopic")
    )
    @JsonManagedReference
    private Subtopic subtopic;

    @ManyToOne
    @JoinColumn(
            name = "created_by",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_question_user")
    )
    @JsonManagedReference
    private User createdBy;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Option> options;

    @OneToOne(
            mappedBy = "question",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private Solution solution;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<ExamQuestion> exams;
}

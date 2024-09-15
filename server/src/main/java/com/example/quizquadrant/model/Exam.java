package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "VARCHAR(50)"
    )
    private String title;

    @Column(
            name = "start_date_time",
            nullable = false,
            columnDefinition = "DATETIME"
    )
    private LocalDateTime startDateTime;

    @Column(
            name = "duration_in_minutes",
            nullable = false,
            columnDefinition = "INT(3)"
    )
    private Integer durationInMinutes;

    @Column(
            name = "is_result_generated",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean isResultGenerated;

    @Column(
            name = "total_marks",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Integer totalMarks;

    @Column(
            name = "last_modified_on",
            nullable = false,
            columnDefinition = "DATETIME"
    )
    private LocalDateTime lastModifiedOn;

    @ManyToOne
    @JoinColumn(
            name = "created_by",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_exam_user")
    )
    @JsonManagedReference
    private User createdBy;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<ExamQuestion> questions;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<ExamCandidate> candidates;
}
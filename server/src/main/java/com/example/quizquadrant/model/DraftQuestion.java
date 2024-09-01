package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "draft_question")
public class DraftQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "data",
            nullable = false,
            columnDefinition = "JSON"
    )
    private String data;

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
            foreignKey = @ForeignKey(name = "fk_draftquestion_user")
    )
    @JsonBackReference
    private User createdBy;

}

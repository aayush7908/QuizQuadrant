//package com.example.quizquadrant.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "_draft_exam")
//public class DraftExam {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @Column(
//            name = "data",
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
//    private String data;
//
//    @Column(
//            name = "last_modified_on",
//            nullable = false,
//            columnDefinition = "TIMESTAMP"
//    )
//    private LocalDateTime lastModifiedOn;
//
//    @ManyToOne
//    @JoinColumn(
//            name = "created_by",
//            nullable = false,
//            referencedColumnName = "id",
//            foreignKey = @ForeignKey(name = "fk_draftexam_user")
//    )
//    @JsonBackReference
//    private User createdBy;
//
//}

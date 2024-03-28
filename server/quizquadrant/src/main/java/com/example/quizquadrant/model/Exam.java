package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @SequenceGenerator(
            name = "exam_sequence",
            sequenceName = "exam_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exam_sequence"
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition ="VARCHAR(200)"
    )
    private String title;

    @Column(
            name = "startDateTime",
            nullable = false,
            columnDefinition = "DATETIME"
    )
    private LocalDateTime startDateTime;

    @Column(
            name = "isResultGenerated",
            nullable = false
    )
    private Boolean isResultGenerated;

    @Column(
            name = "duration",
            nullable = false,
            columnDefinition = "int(4)"
    )
    private Integer duration;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Result> examResults;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<ExamResponses> examResponses;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<PrivateQuestion> privateQuestions;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "creator")
    private User creator;




//    constructors

    public Exam(
            String title,
            LocalDateTime startDateTime,
            Boolean isResultGenerated,
            Integer duration,
            User creator
    ) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.isResultGenerated = isResultGenerated;
        this.duration = duration;
        this.creator = creator;
    }

}

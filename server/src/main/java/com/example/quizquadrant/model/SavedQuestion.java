package com.example.quizquadrant.model;


import com.example.quizquadrant.model.key.SavedQuestionKey;
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
@Table(name = "_saved_question")
@IdClass(SavedQuestionKey.class)
public class SavedQuestion {

    @Id
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_savedquestion_user")
    )
    @JsonBackReference
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_savedquestion_question")
    )
    @JsonManagedReference
    private Question question;

}

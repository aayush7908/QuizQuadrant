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
@Table(name = "otp")
public class Otp {

    @Id
    private String email;

    private String otp;

    @Column(
            name = "expires_on",
            nullable = false,
            columnDefinition = "DATETIME"
    )
    private LocalDateTime expiresOn;

}

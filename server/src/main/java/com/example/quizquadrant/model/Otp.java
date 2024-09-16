package com.example.quizquadrant.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_otp")
public class Otp {

    @Id
    private String email;

    @Column(
            name = "otp",
            nullable = false
    )
    private String otp;

    @Column(
            name = "expires_on",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime expiresOn;

}

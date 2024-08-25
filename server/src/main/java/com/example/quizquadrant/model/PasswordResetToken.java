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
@Table(name = "otp")
public class PasswordResetToken {

    @Id
    private String email;

    private String token;

    @Column(
            name = "expires_on",
            nullable = false,
            columnDefinition = "DATETIME"
    )
    private LocalDateTime expiresOn;

}

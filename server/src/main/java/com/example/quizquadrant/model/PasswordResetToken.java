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
@Table(name = "_password_reset_token")
public class PasswordResetToken {

    @Id
    private String email;

    @Column(
            name = "token",
            nullable = false,
            columnDefinition = "VARCHAR(36)"
    )
    private String token;

    @Column(
            name = "expires_on",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime expiresOn;

}

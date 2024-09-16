package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {

    @Query("SELECT p from PasswordResetToken p WHERE p.email = :email AND p.token = :token")
    Optional<PasswordResetToken> findByEmailAndToken(String email, String token);

}

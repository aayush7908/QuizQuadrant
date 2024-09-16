package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String> {

    @Query("SELECT o from Otp o WHERE o.email = :email AND o.otp = :otp")
    Optional<Otp> findByEmailAndOtp(String email, String otp);

}

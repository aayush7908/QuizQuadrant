package com.example.quizquadrant.utils.passwordresettoken;

public interface PasswordResetTokenService {

    String generatePasswordResetToken(String email);

    void validatePasswordResetToken(String email, String token) throws Exception;

}

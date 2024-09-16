package com.example.quizquadrant.utils.passwordresettoken;

import com.example.quizquadrant.model.PasswordResetToken;

public interface PasswordResetTokenService {

    String generatePasswordResetToken(String email);

    void validatePasswordResetToken(
            String email,
            String token
    ) throws Exception;

    //    repository access methods
    PasswordResetToken createOrUpdatePasswordResetToken(
            String email
    );
}

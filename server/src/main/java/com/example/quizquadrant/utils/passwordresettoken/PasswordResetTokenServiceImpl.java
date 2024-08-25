package com.example.quizquadrant.utils.passwordresettoken;

import com.example.quizquadrant.model.PasswordResetToken;
import com.example.quizquadrant.repository.PasswordResetTokenRepository;
import com.example.quizquadrant.utils.error.UnauthorizedAccessError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final int TOKEN_LIFE = 10 * 60 * 1000;  // 10 minutes

    @Override
    public String generatePasswordResetToken(String email) {
//        generate token
        PasswordResetToken newPasswordResetToken = PasswordResetToken
                .builder()
                .email(email)
                .token(generateToken())
                .expiresOn((new Timestamp(System.currentTimeMillis() + TOKEN_LIFE)).toLocalDateTime())
                .build();


//        check if token is already present and act accordingly
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepository.findById(email);
        if (passwordResetTokenOptional.isEmpty()) {
            passwordResetTokenRepository.save(newPasswordResetToken);
        } else {
            PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
            passwordResetToken.setToken(newPasswordResetToken.getToken());
            passwordResetToken.setExpiresOn(newPasswordResetToken.getExpiresOn());
            passwordResetTokenRepository.save(passwordResetToken);
        }

        return newPasswordResetToken.getToken();
    }

    @Override
    public void validatePasswordResetToken(String email, String token) throws Exception {
        boolean isTokenValid = isTokenValid(email, token);
        if (!isTokenValid) {
            throw new UnauthorizedAccessError();
        }
    }


    //    helper methods for internal call

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private boolean isTokenValid(String email, String token) {
//        check if token exists in database
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepository.findByEmailAndToken(email, token);
        if (passwordResetTokenOptional.isEmpty()) {
            return false;
        }

//        check if token is expired
        PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
        passwordResetTokenRepository.delete(passwordResetToken);
        return LocalDateTime.now().isBefore(passwordResetToken.getExpiresOn());
    }

}

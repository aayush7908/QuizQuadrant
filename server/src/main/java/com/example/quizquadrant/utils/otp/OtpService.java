package com.example.quizquadrant.utils.otp;

import com.example.quizquadrant.utils.email.EmailDetails;
import com.example.quizquadrant.utils.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface OtpService {

    void sendOtpMail(String email);

    void validateOtp(String email, String otp) throws Exception;

}

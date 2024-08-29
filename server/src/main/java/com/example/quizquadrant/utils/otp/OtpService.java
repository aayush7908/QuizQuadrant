package com.example.quizquadrant.utils.otp;

public interface OtpService {

    void sendOtp(String email);

    void validateOtp(String email, String otp) throws Exception;

}

package com.example.quizquadrant.utils.otp;

import com.example.quizquadrant.model.Otp;

public interface OtpService {

    void sendOtpToVerifyEmail(String email);

    void sendOtpToResetPassword(String email);

    void validateOtp(String email, String otp) throws Exception;

    //    repository access methods
    Otp createOrUpdateOtp(String email);

}

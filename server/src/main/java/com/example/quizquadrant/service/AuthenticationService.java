package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    //    controller service methods
    ResponseEntity<AuthenticationResponseDto> register(
            RegisterRequestDto registerRequestDto
    ) throws Exception;

    ResponseEntity<AuthenticationResponseDto> login(
            LoginRequestDto loginRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> sendOtp(
            OtpRequestDto otpRequestDto
    ) throws Exception;

    ResponseEntity<SendPasswordResetTokenResponseDto> sendPasswordResetToken(
            SendPasswordResetTokenRequestDto sendPasswordResetTokenRequestDto
    ) throws Exception;

}

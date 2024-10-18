package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.authentication.*;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    //    controller service methods
    ResponseEntity<AuthenticationResponseDto> register(
            RegisterRequestDto registerRequestDto
    ) throws Exception;

    ResponseEntity<AuthenticationResponseDto> login(
            LoginRequestDto loginRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> sendResetPasswordOtp(
            SendResetPasswordOtpRequestDto sendResetPasswordOtpRequestDto
    ) throws Exception;

    ResponseEntity<VerifyResetPasswordOtpResponseDto> verifyResetPasswordOtp(
            VerifyResetPasswordOtpRequestDto verifyResetPasswordOtpRequestDto
    ) throws Exception;

}

package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.authentication.*;
import com.example.quizquadrant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    //    ROUTE: 1 => Register a new user
    //    POST: "/api/auth/register"
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) throws Exception {
        return authenticationService.register(registerRequestDto);
    }


    //    ROUTE: 2 => Authenticate a user using email and password
    //    POST: "/api/auth/login"
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) throws Exception {
        return authenticationService.login(loginRequestDto);
    }


    //    ROUTE: 3 => Send OTP to a user for Forgot Password Service
    //    POST: "/api/auth/reset-password/otp/send"
    @PostMapping("/reset-password/otp/send")
    public ResponseEntity<BooleanResponseDto> sendResetPasswordOtp(
            @RequestBody SendResetPasswordOtpRequestDto sendResetPasswordOtpRequestDto
    ) throws Exception {
        return authenticationService.sendResetPasswordOtp(sendResetPasswordOtpRequestDto);
    }


    //    ROUTE: 4 => Verify OTP and send a PasswordResetToken for Forgot Password Service
    //    POST: "/api/auth/reset-password/otp/verify"
    @PostMapping("/reset-password/otp/verify")
    public ResponseEntity<VerifyResetPasswordOtpResponseDto> verifyResetPasswordOtp(
            @RequestBody VerifyResetPasswordOtpRequestDto verifyResetPasswordOtpRequestDto
    ) throws Exception {
        return authenticationService.verifyResetPasswordOtp(verifyResetPasswordOtpRequestDto);
    }

}

package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
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

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) throws Exception {
        return authenticationService.register(registerRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) throws Exception {
        return authenticationService.login(loginRequestDto);
    }

    @PostMapping("/send/otp")
    public ResponseEntity<BooleanResponseDto> sendOtp(
            @RequestBody OtpRequestDto otpRequestDto
    ) throws Exception {
        return authenticationService.sendOtp(otpRequestDto);
    }

    @PostMapping("/send/password-reset-token")
    public ResponseEntity<SendPasswordResetTokenResponseDto> sendPasswordResetToken(
            @RequestBody SendPasswordResetTokenRequestDto sendPasswordResetTokenRequestDto
    ) throws Exception {
        return authenticationService.sendPasswordResetToken(sendPasswordResetTokenRequestDto);
    }

}

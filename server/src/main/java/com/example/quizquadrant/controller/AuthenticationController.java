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

    @PostMapping("/forgot-password")
    public ResponseEntity<BooleanResponseDto> forgotPassword(
            @RequestBody LoginRequestDto loginRequestDto
    ) throws Exception {
        return authenticationService.forgotPassword(loginRequestDto);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<BooleanResponseDto> resendOtp(
            @RequestBody LoginRequestDto loginRequestDto
    ) throws Exception {
        return authenticationService.forgotPassword(loginRequestDto);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<AuthenticationResponseDto> verifyEmail(
            @RequestBody VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception {
        return authenticationService.verifyEmail(verifyEmailRequestDto);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate() throws Exception {
        return authenticationService.authenticate();
    }

}

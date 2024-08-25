package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<AuthenticationResponseDto> register(
            RegisterRequestDto registerRequestDto
    ) throws Exception;

    ResponseEntity<AuthenticationResponseDto> login(
            LoginRequestDto loginRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> forgotPassword(
            LoginRequestDto loginRequestDto
    ) throws Exception;

    ResponseEntity<AuthenticationResponseDto> verifyEmail(
            VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception;

    ResponseEntity<AuthenticationResponseDto> authenticate() throws Exception;

}

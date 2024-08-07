package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.AuthenticationRequestDto;
import com.example.quizquadrant.dto.AuthenticationResponseDto;
import com.example.quizquadrant.dto.RegisterRequestDto;
import com.example.quizquadrant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        AuthenticationResponseDto authentictionResponseDto = authenticationService.register(request);
        if(authentictionResponseDto != null) {
            return ResponseEntity.ok(authentictionResponseDto);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate() {
        return ResponseEntity.ok(authenticationService.authenticate());
    }

}

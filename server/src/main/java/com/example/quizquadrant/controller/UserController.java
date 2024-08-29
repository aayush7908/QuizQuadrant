package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.service.AuthenticationService;
import com.example.quizquadrant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/verify-email")
    public ResponseEntity<BooleanResponseDto> verifyEmail(
            @RequestBody VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception {
        return userService.verifyEmail(verifyEmailRequestDto);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<BooleanResponseDto> resetPassword(
            @RequestBody ResetPasswordDto resetPasswordDto
    ) throws Exception {
        return userService.resetPassword(resetPasswordDto);
    }

    @PatchMapping("/update/name")
    public ResponseEntity<BooleanResponseDto> updateName(
            @RequestBody UserDto userDto
    ) throws Exception {
        return userService.updateName(userDto);
    }

    @PatchMapping("/update/profile-image")
    public ResponseEntity<BooleanResponseDto> updateProfileImage(
            @RequestBody UserDto userDto
    ) throws Exception {
        return userService.updateProfileImage(userDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BooleanResponseDto> delete() throws Exception {
        return userService.delete();
    }

    @GetMapping("/get/profile")
    public ResponseEntity<UserDto> getProfile() throws Exception {
        return userService.getProfile();
    }

}
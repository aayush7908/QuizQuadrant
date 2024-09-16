package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
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

    @PostMapping("/reset-password")
    public ResponseEntity<BooleanResponseDto> resetPassword(
            @RequestBody ResetPasswordDto resetPasswordDto
    ) throws Exception {
        return userService.resetPassword(resetPasswordDto);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate() {
        return userService.authenticate();
    }

    @PostMapping("/verify-email")
    public ResponseEntity<BooleanResponseDto> verifyEmail(
            @RequestBody VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception {
        return userService.verifyEmail(verifyEmailRequestDto);
    }

    @PatchMapping("/update/name")
    public ResponseEntity<BooleanResponseDto> updateName(
            @RequestBody UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception {
        return userService.updateName(updateUserNameRequestDto);
    }

    @PatchMapping("/update/profile-image")
    public ResponseEntity<BooleanResponseDto> updateProfileImage(
            @RequestBody UpdateUserProfileImageRequestDto updateUserProfileImageRequestDto
    ) throws Exception {
        return userService.updateProfileImage(updateUserProfileImageRequestDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BooleanResponseDto> delete() throws Exception {
        return userService.delete();
    }

    @GetMapping("/get/profile")
    public ResponseEntity<UserProfileResponseDto> getProfile() throws Exception {
        return userService.getProfile();
    }

}

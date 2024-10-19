package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.authentication.AuthenticationResponseDto;
import com.example.quizquadrant.dto.user.*;
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


    //    ROUTE: 1 => Authenticate user using JWT Token
    //    GET: "/api/user/authenticate"
    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate() {
        return userService.authenticate();
    }


    //    ROUTE: 2 => Reset Password
    //    POST: "/api/user/reset-password"
    @PostMapping("/reset-password")
    public ResponseEntity<BooleanResponseDto> resetPassword(
            @RequestBody ResetPasswordRequestDto resetPasswordRequestDto
    ) throws Exception {
        return userService.resetPassword(resetPasswordRequestDto);
    }


    //    ROUTE: 3 => Send Otp for Email Verification Service
    //    POST: "/api/user/verify-email/otp/send"
    @PostMapping("/verify-email/otp/send")
    public ResponseEntity<BooleanResponseDto> sendVerifyEmailOtp() throws Exception {
        return userService.sendVerifyEmailOtp();
    }


    //    ROUTE: 4 => Verify Otp for Email Verification
    //    POST: "/api/user/verify-email/otp/verify"
    @PostMapping("/verify-email/otp/verify")
    public ResponseEntity<BooleanResponseDto> verifyVerifyEmailOtp(
            @RequestBody VerifyEmailOtpRequestDto verifyEmailOtpRequestDto
    ) throws Exception {
        return userService.verifyVerifyEmailOtp(verifyEmailOtpRequestDto);
    }


    //    ROUTE: 5 => Update name of authenticated user
    //    PATCH: "/api/user/update/name"
    @PatchMapping("/update/name")
    public ResponseEntity<BooleanResponseDto> updateName(
            @RequestBody UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception {
        return userService.updateName(updateUserNameRequestDto);
    }


    //    ROUTE: 6 => Update profileImage of authenticated user
    //    PATCH: "/api/user/update/profile-image"
    @PatchMapping("/update/profile-image")
    public ResponseEntity<BooleanResponseDto> updateProfileImage(
            @RequestBody UpdateUserProfileImageRequestDto updateUserProfileImageRequestDto
    ) throws Exception {
        return userService.updateProfileImage(updateUserProfileImageRequestDto);
    }


    //    ROUTE: 7 => Delete authenticated user account and related information
    //    DELETE: "/api/user/delete"
    @DeleteMapping("/delete")
    public ResponseEntity<BooleanResponseDto> delete() throws Exception {
        return userService.delete();
    }


    //    ROUTE: 8 => Get profile of authenticated user
    //    GET: "/api/user/get/profile"
    @GetMapping("/get/profile")
    public ResponseEntity<UserProfileResponseDto> getProfile() throws Exception {
        return userService.getProfile();
    }

}

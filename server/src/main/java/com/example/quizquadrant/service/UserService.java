package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    //    controller service methods
    ResponseEntity<BooleanResponseDto> verifyEmail(
            VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> resetPassword(
            ResetPasswordDto resetPasswordDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> updateName(
            UserDto userDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> updateProfileImage(
            UserDto userDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> delete() throws Exception;

    ResponseEntity<UserDto> getProfile() throws Exception;


    //    helper methods
    User getUserByEmail(
            String email
    ) throws Exception;

    void authorizeUser(
            User user
    ) throws Exception;

}

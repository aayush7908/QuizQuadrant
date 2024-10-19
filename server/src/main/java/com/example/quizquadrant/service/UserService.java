package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.authentication.AuthenticationResponseDto;
import com.example.quizquadrant.dto.user.*;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    //    controller service methods
    ResponseEntity<BooleanResponseDto> resetPassword(
            ResetPasswordRequestDto resetPasswordRequestDto
    ) throws Exception;

    ResponseEntity<AuthenticationResponseDto> authenticate();

    ResponseEntity<BooleanResponseDto> sendVerifyEmailOtp() throws Exception;

    ResponseEntity<BooleanResponseDto> verifyVerifyEmailOtp(
            VerifyEmailOtpRequestDto verifyEmailOtpRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> updateName(
            UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> updateProfileImage(
            UpdateUserProfileImageRequestDto updateUserProfileImageRequestDto
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> delete() throws Exception;

    ResponseEntity<UserProfileResponseDto> getProfile() throws Exception;


    //    repository access methods
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(UUID id);

    User getUserByEmail(
            String email
    ) throws Exception;

    User getUserById(
            UUID id
    ) throws Exception;

    List<User> getAllUsers(
            Integer pageNumber,
            Integer pageSize
    );

    boolean checkUserExistsByEmail(
            String email
    );

    Integer countTotalUsers();


    //    helper methods
    void authorizeUser(
            User user
    ) throws Exception;

    User getAuthenticatedUser();

}

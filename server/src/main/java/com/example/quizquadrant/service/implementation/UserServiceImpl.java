package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.repository.UserRepository;
import com.example.quizquadrant.service.UserService;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.otp.OtpService;
import com.example.quizquadrant.utils.passwordresettoken.PasswordResetTokenService;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final OtpService otpService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<BooleanResponseDto> verifyEmail(
            VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateVerifyEmailInput(verifyEmailRequestDto);

//        get user from email
        User user = getUserByEmail(verifyEmailRequestDto.email());

//        validate otp
        otpService.validateOtp(verifyEmailRequestDto.email(), verifyEmailRequestDto.otp());

//        update user data
        user.setEmailVerifiedOn(LocalDateTime.now());
        userRepository.save(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        BooleanResponseDto
                                .builder()
                                .success(true)
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> resetPassword(
            ResetPasswordDto resetPasswordDto
    ) throws Exception {
//        validate email and password
        validationService.validateEmail(resetPasswordDto.email());
        validationService.validatePassword(resetPasswordDto.password());

//        fetch user by email
        User user = getUserByEmail(resetPasswordDto.email());

//        validate password reset token
        passwordResetTokenService.validatePasswordResetToken(resetPasswordDto.email(), resetPasswordDto.token());

//        reset password and save user in database
        user.setPassword(passwordEncoder.encode(resetPasswordDto.password()));
        userRepository.save(user);

//        response
        return ResponseEntity.status(200).body(BooleanResponseDto.builder().success(true).build());
    }

    @Override
    public ResponseEntity<BooleanResponseDto> updateName(
            UserDto userDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateUserNameInput(userDto);

//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        authorizeUser(user);

//        update user data and save
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        userRepository.save(user);

//        response
        return ResponseEntity.status(200).body(BooleanResponseDto.builder().success(true).build());
    }

    @Override
    public ResponseEntity<BooleanResponseDto> updateProfileImage(
            UserDto userDto
    ) throws Exception {
//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        authorizeUser(user);

//        update user data and save
        user.setProfileImageUrl(userDto.profileImageUrl());
        userRepository.save(user);

//        response
        return ResponseEntity.status(200).body(BooleanResponseDto.builder().success(true).build());
    }

    @Override
    public ResponseEntity<BooleanResponseDto> delete() throws Exception {
//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        authorize user
        authorizeUser(user);

//        remove user from database
        userRepository.deleteById(user.getId());

//        response
        return ResponseEntity.status(200).body(BooleanResponseDto.builder().success(true).build());
    }

    @Override
    public ResponseEntity<UserDto> getProfile() throws Exception {
//        fetch authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        response
        return ResponseEntity
                .status(200)
                .body(
                        UserDto
                                .builder()
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .profileImageUrl(user.getProfileImageUrl())
                                .accountCreatedOn(user.getAccountCreatedOn())
                                .role(user.getRole().name())
                                .isEmailVerified(user.getEmailVerifiedOn() != null)
                                .build()
                );
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new NotFoundError("User not found");
        }
        return userOptional.get();
    }

    @Override
    public User getUserById(
            UUID id
    ) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundError("User not found");
        }
        return userOptional.get();
    }

    @Override
    public void authorizeUser(
            User user
    ) throws Exception {
        if (user.getEmailVerifiedOn() == null) {
            throw new AccessDeniedError("Email not verified");
        }
    }
}

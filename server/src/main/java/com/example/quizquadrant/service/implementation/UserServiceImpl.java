package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.mapper.AuthenticationResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.UserProfileResponseDtoMapper;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.UserRepository;
import com.example.quizquadrant.service.UserService;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.error.UnauthorizedAccessError;
import com.example.quizquadrant.utils.jwt.JwtService;
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
    private final JwtService jwtService;
    private final ValidationService validationService;
    private final OtpService otpService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationResponseDtoMapper authenticationResponseDtoMapper;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;
    private final UserProfileResponseDtoMapper userProfileResponseDtoMapper;


    //    controller service methods
    @Override
    public ResponseEntity<BooleanResponseDto> resetPassword(
            ResetPasswordDto resetPasswordDto
    ) throws Exception {
//        validate email and password
        validationService.validateEmail(resetPasswordDto.email());
        validationService.validatePassword(resetPasswordDto.password());

        try {
//        fetch user by email
            User user = getUserByEmail(resetPasswordDto.email());

//        validate password reset token
            passwordResetTokenService.validatePasswordResetToken(
                    resetPasswordDto.email(),
                    resetPasswordDto.token()
            );

//        reset password and save user in database
            user.setPassword(passwordEncoder.encode(resetPasswordDto.password()));
            updateUser(user);
        } catch (Exception e) {
            throw new UnauthorizedAccessError();
        }

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<AuthenticationResponseDto> authenticate() {
//        get authenticated user
        User user = getAuthenticatedUser();

//        generate a new jwt token
        String token = jwtService.generateToken(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        authenticationResponseDtoMapper
                                .toDto(token, user)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> verifyEmail(
            VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception {
//        fetch authenticated user
        User user = getAuthenticatedUser();

//        validate otp
        otpService.validateOtp(
                user.getEmail(),
                verifyEmailRequestDto.otp()
        );

//        update user data
        user.setEmailVerifiedOn(LocalDateTime.now());
        updateUser(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> updateName(
            UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateUpdateUserNameInput(updateUserNameRequestDto);

//        fetch authenticated user
        User user = getAuthenticatedUser();

//        authorize user
        authorizeUser(user);

//        update user data and save
        user.setFirstName(updateUserNameRequestDto.firstName());
        user.setLastName(updateUserNameRequestDto.lastName());
        updateUser(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> updateProfileImage(
            UpdateUserProfileImageRequestDto updateUserProfileImageRequestDto
    ) throws Exception {
//        fetch authenticated user
        User user = getAuthenticatedUser();

//        authorize user
        authorizeUser(user);

//        update user data and save
        user.setProfileImageUrl(
                updateUserProfileImageRequestDto.profileImageUrl()
        );
        updateUser(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> delete() throws Exception {
//        fetch authenticated user
        User user = getAuthenticatedUser();

//        authorize user
        authorizeUser(user);

//        remove user from database
        deleteUser(user.getId());

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<UserProfileResponseDto> getProfile() throws Exception {
//        fetch authenticated user
        User user = getAuthenticatedUser();

//        response
        return ResponseEntity
                .status(200)
                .body(
                        userProfileResponseDtoMapper
                                .toDto(user)
                );
    }


    //    repository access methods
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(
            String email
    ) throws Exception {
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
    public boolean checkUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    //    helper methods
    @Override
    public void authorizeUser(
            User user
    ) throws Exception {
        if (
                user.getRole() != Role.ADMIN &&
                        user.getEmailVerifiedOn() == null
        ) {
            throw new AccessDeniedError("Email Not Verified");
        }
    }

    @Override
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}

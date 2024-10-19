package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.authentication.AuthenticationResponseDto;
import com.example.quizquadrant.dto.mapper.AuthenticationResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.UserProfileResponseDtoMapper;
import com.example.quizquadrant.dto.user.*;
import com.example.quizquadrant.model.Otp;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.UserRepository;
import com.example.quizquadrant.service.UserService;
import com.example.quizquadrant.utils.email.EmailService;
import com.example.quizquadrant.utils.error.AccessDeniedError;
import com.example.quizquadrant.utils.error.NotFoundError;
import com.example.quizquadrant.utils.error.UnauthorizedAccessError;
import com.example.quizquadrant.utils.jwt.JwtService;
import com.example.quizquadrant.utils.otp.OtpService;
import com.example.quizquadrant.utils.passwordresettoken.PasswordResetTokenService;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ValidationService validationService;
    private final OtpService otpService;
    private final EmailService emailService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationResponseDtoMapper authenticationResponseDtoMapper;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;
    private final UserProfileResponseDtoMapper userProfileResponseDtoMapper;


    //    controller service methods
    @Override
    public ResponseEntity<BooleanResponseDto> resetPassword(
            ResetPasswordRequestDto resetPasswordRequestDto
    ) throws Exception {
//        validate email and password
        validationService.validateEmail(resetPasswordRequestDto.email());
        validationService.validatePassword(resetPasswordRequestDto.password());

        try {
//        fetch user by email
            User user = getUserByEmail(resetPasswordRequestDto.email());

//        validate password reset token
            passwordResetTokenService.validatePasswordResetToken(
                    resetPasswordRequestDto.email(),
                    resetPasswordRequestDto.token()
            );

//        reset password and save user in database
            user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.password()));
            updateUser(user);

//            send confirmation email
            emailService.sendConfirmationMail(
                    resetPasswordRequestDto.email(),
                    "Password Changed",
                    "Password for your account has been changed. Please login again to continue."
            );

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
    public ResponseEntity<BooleanResponseDto> sendVerifyEmailOtp() throws Exception {
//        fetch authenticated user
        User user = getAuthenticatedUser();

//        generate otp
        Otp otp = otpService.createOrUpdateOtp(user.getEmail());

//        send otp for email verification
        emailService.sendEmailVerificationOtp(
                user.getEmail(),
                otp.getOtp()
        );

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> verifyVerifyEmailOtp(
            VerifyEmailOtpRequestDto verifyEmailOtpRequestDto
    ) throws Exception {
//        fetch authenticated user
        User user = getAuthenticatedUser();

//        validate otp
        otpService.validateOtp(
                user.getEmail(),
                verifyEmailOtpRequestDto.otp()
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
    public List<User> getAllUsers(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }

    @Override
    public boolean checkUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Integer countTotalUsers() {
        return userRepository.countAll();
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

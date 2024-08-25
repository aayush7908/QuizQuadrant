package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.repository.UserRepository;
import com.example.quizquadrant.service.AuthenticationService;
import com.example.quizquadrant.service.UserService;
import com.example.quizquadrant.utils.error.DuplicateDataError;
import com.example.quizquadrant.utils.error.UnauthorizedAccessError;
import com.example.quizquadrant.utils.jwt.JwtService;
import com.example.quizquadrant.utils.otp.OtpService;
import com.example.quizquadrant.utils.passwordresettoken.PasswordResetTokenService;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Override
    public ResponseEntity<AuthenticationResponseDto> register(
            RegisterRequestDto registerRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateRegisterInput(registerRequestDto);

//        check if email is already registered
        if (userRepository.existsByEmail(registerRequestDto.email())) {
            throw new DuplicateDataError("Email already exists");
        }

//        store user in database
        User user = userRepository.save(
                User
                        .builder()
                        .email(registerRequestDto.email())
                        .password(passwordEncoder.encode(registerRequestDto.password()))
                        .firstName(registerRequestDto.firstName())
                        .lastName(registerRequestDto.lastName())
                        .accountCreatedOn(LocalDateTime.now())
                        .isEmailVerified(false)
                        .role(Role.valueOf(registerRequestDto.role()))
                        .build()
        );

//        send otp for email verification
        otpService.sendOtpMail(registerRequestDto.email());

//        generate jwt token
        String token = jwtService.generateToken(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        AuthenticationResponseDto
                                .builder()
                                .token(token)
                                .user(
                                        UserDto
                                                .builder()
                                                .id(user.getId())
                                                .email(user.getEmail())
                                                .isEmailVerified(user.getIsEmailVerified())
                                                .build()
                                )
                                .build()
                );
    }

    @Override
    public ResponseEntity<AuthenticationResponseDto> login(
            LoginRequestDto loginRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateLoginInput(loginRequestDto);

//        authenticate email and password
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.email(),
                            loginRequestDto.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedAccessError("Incorrect credentials");
        }

//        fetch user by email
        User user = userService.getUserByEmail(loginRequestDto.email());

//        generate jwt token
        String token = jwtService.generateToken(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        AuthenticationResponseDto
                                .builder()
                                .token(token)
                                .user(
                                        UserDto
                                                .builder()
                                                .id(user.getId())
                                                .email(user.getEmail())
                                                .isEmailVerified(user.getIsEmailVerified())
                                                .build()
                                )
                                .build()
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> forgotPassword(
            LoginRequestDto loginRequestDto
    ) throws Exception {
//        validate email
        validationService.validateEmail(loginRequestDto.email());

//        send email containing otp
        otpService.sendOtpMail(loginRequestDto.email());

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
    public ResponseEntity<AuthenticationResponseDto> verifyEmail(
            VerifyEmailRequestDto verifyEmailRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateVerifyEmailInput(verifyEmailRequestDto);

//        get user from email
        User user = userService.getUserByEmail(verifyEmailRequestDto.email());

//        validate otp
        otpService.validateOtp(verifyEmailRequestDto.email(), verifyEmailRequestDto.otp());

//        generate password reset token
        String token = passwordResetTokenService.generatePasswordResetToken(verifyEmailRequestDto.email());

//        response
        return ResponseEntity
                .status(200)
                .body(
                        AuthenticationResponseDto
                                .builder()
                                .token(token)
                                .build()
                );
    }

    @Override
    public ResponseEntity<AuthenticationResponseDto> authenticate() throws Exception {
//        get authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        generate a new jwt token
        String token = jwtService.generateToken(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        AuthenticationResponseDto
                                .builder()
                                .token(token)
                                .user(
                                        UserDto
                                                .builder()
                                                .id(user.getId())
                                                .email(user.getEmail())
                                                .isEmailVerified(user.getIsEmailVerified())
                                                .build()
                                )
                                .build()
                );
    }

}

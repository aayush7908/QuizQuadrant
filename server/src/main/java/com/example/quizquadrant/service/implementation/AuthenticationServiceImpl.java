package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.mapper.AuthenticationResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.service.AuthenticationService;
import com.example.quizquadrant.service.UserService;
import com.example.quizquadrant.utils.error.BadRequestError;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final AuthenticationResponseDtoMapper authenticationResponseDtoMapper;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;

    @Override
    public ResponseEntity<AuthenticationResponseDto> register(
            RegisterRequestDto registerRequestDto
    ) throws Exception {
//        validate input data
        validationService.validateRegisterInput(registerRequestDto);

//        check if email is already registered
        boolean isEmailRegistered = userService.checkUserExistsByEmail(
                registerRequestDto.email()
        );
        if (isEmailRegistered) {
            throw new DuplicateDataError("Email Already Exists");
        }

//        store user in database
        User user = userService.createUser(
                User
                        .builder()
                        .email(registerRequestDto.email())
                        .password(
                                passwordEncoder
                                        .encode(registerRequestDto.password())
                        )
                        .firstName(registerRequestDto.firstName())
                        .lastName(registerRequestDto.lastName())
                        .accountCreatedOn(LocalDateTime.now())
                        .role(Role.valueOf(registerRequestDto.role()))
                        .build()
        );

//        send otp for email verification
        otpService.sendOtpToVerifyEmail(registerRequestDto.email());

//        generate jwt token
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
            throw new UnauthorizedAccessError("Incorrect Credentials");
        }

//        fetch user by email
        User user = userService.getUserByEmail(loginRequestDto.email());

//        generate jwt token
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
    public ResponseEntity<BooleanResponseDto> sendOtp(
            OtpRequestDto otpRequestDto
    ) throws Exception {
//        validate email
        validationService.validateEmail(otpRequestDto.email());

        try {
//            check if user exists
            userService.getUserByEmail(otpRequestDto.email());
//            send email containing otp
            otpService.sendOtpToResetPassword(otpRequestDto.email());
        } catch (Exception ignore) {
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
    public ResponseEntity<SendPasswordResetTokenResponseDto> sendPasswordResetToken(
            SendPasswordResetTokenRequestDto sendPasswordResetTokenRequestDto
    ) throws Exception {
//        validate email
        validationService.validateEmail(sendPasswordResetTokenRequestDto.email());

        String token = null;

        try {
//            check if user exists
            userService.getUserByEmail(sendPasswordResetTokenRequestDto.email());

//            validate otp
            otpService.validateOtp(
                    sendPasswordResetTokenRequestDto.email(),
                    sendPasswordResetTokenRequestDto.otp()
            );

//            generate password reset token
            token = passwordResetTokenService
                    .generatePasswordResetToken(sendPasswordResetTokenRequestDto.email());
        } catch (Exception e) {
            throw new BadRequestError("Invalid OTP");
        }

//        response
        return ResponseEntity
                .status(200)
                .body(
                        SendPasswordResetTokenResponseDto
                                .builder()
                                .token(token)
                                .build()
                );
    }

}

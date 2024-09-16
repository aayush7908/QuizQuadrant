package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.AuthenticationResponseDto;
import com.example.quizquadrant.dto.UserProfileResponseDto;
import com.example.quizquadrant.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponseDtoMapper {

    public AuthenticationResponseDto toDto(
            String token,
            User user
    ) {
        return AuthenticationResponseDto
                .builder()
                .token(token)
                .user(
                        UserProfileResponseDto
                                .builder()
                                .email(user.getEmail())
                                .isEmailVerified(user.getEmailVerifiedOn() != null)
                                .role(user.getRole().name())
                                .build()
                )
                .build();
    }

}

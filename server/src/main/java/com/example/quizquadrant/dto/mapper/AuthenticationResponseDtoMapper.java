package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.authentication.AuthenticatedUserDto;
import com.example.quizquadrant.dto.authentication.AuthenticationResponseDto;
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
                        AuthenticatedUserDto
                                .builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .profileImageUrl(user.getProfileImageUrl())
                                .role(user.getRole().name())
                                .isEmailVerified(user.getEmailVerifiedOn() != null)
                                .build()
                )
                .build();
    }

}

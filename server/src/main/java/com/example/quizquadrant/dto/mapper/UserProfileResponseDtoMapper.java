package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.UserProfileResponseDto;
import com.example.quizquadrant.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileResponseDtoMapper {

    public UserProfileResponseDto toDto(
            User user
    ) {
        return UserProfileResponseDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImageUrl(user.getProfileImageUrl())
                .accountCreatedOn(user.getAccountCreatedOn())
                .role(user.getRole().name())
                .isEmailVerified(user.getEmailVerifiedOn() != null)
                .totalUsers(0)
                .build();
    }

}

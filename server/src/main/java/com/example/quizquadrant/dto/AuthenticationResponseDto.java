package com.example.quizquadrant.dto;

import com.example.quizquadrant.dto.UserDto;
import lombok.Builder;

@Builder
public record AuthenticationResponseDto(
        String token,
        UserProfileResponseDto user
) {
}

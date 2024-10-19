package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.user.UpdateUserNameRequestDto;
import com.example.quizquadrant.dto.user.UserProfileResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    //    controller service methods
    ResponseEntity<List<UserProfileResponseDto>> getAllUsers(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> updateUserName(
            UpdateUserNameRequestDto updateUserNameRequestDto,
            String id
    ) throws Exception;

    ResponseEntity<BooleanResponseDto> deleteUser(
            String id
    ) throws Exception;

}

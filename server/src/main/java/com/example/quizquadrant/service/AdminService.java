package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

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

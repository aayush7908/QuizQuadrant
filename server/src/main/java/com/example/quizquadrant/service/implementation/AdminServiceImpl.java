package com.example.quizquadrant.service.implementation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.mapper.BooleanResponseDtoMapper;
import com.example.quizquadrant.dto.mapper.UserProfileResponseDtoMapper;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.service.AdminService;
import com.example.quizquadrant.service.UserService;
import com.example.quizquadrant.utils.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final ValidationService validationService;
    private final UserProfileResponseDtoMapper userProfileResponseDtoMapper;
    private final BooleanResponseDtoMapper booleanResponseDtoMapper;

    //    controller service methods
    @Override
    public ResponseEntity<List<UserProfileResponseDto>> getAllUsers(
            Integer pageNumber,
            Integer pageSize
    ) throws Exception {
//        validate pageNumber and pageSize
        validationService.validatePageNumber(pageNumber);
        validationService.validatePageSize(pageSize);

//        fetch users
        List<User> users = userService.getAllUsers(pageNumber, pageSize);

//        create list of user dto
        List<UserProfileResponseDto> userProfileResponseDtos = new ArrayList<>();
        for (User user : users) {
            userProfileResponseDtos.add(
                    userProfileResponseDtoMapper
                            .toDto(user)
            );
        }

//        if pageNumber is 0, calculate total number of users
//        and put it into first question
        if (pageNumber == 0) {
            int totalUsers = userService.countTotalUsers();
            userProfileResponseDtos.add(0,
                    UserProfileResponseDto
                            .builder()
                            .totalUsers(totalUsers)
                            .build()
            );
        }

//        response
        return ResponseEntity
                .status(200)
                .body(userProfileResponseDtos);
    }

    @Override
    public ResponseEntity<BooleanResponseDto> updateUserName(
            UpdateUserNameRequestDto updateUserNameRequestDto,
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch user by id
        User user = userService.getUserById(uuid);

//        update user
        user.setFirstName(updateUserNameRequestDto.firstName());
        user.setLastName(updateUserNameRequestDto.lastName());
        userService.updateUser(user);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

    @Override
    public ResponseEntity<BooleanResponseDto> deleteUser(
            String id
    ) throws Exception {
//        validate and get UUID from id-string
        UUID uuid = validationService.validateAndGetUUID(id);

//        fetch user by id
        User user = userService.getUserById(uuid);

//        delete user
        userService.deleteUser(uuid);

//        response
        return ResponseEntity
                .status(200)
                .body(
                        booleanResponseDtoMapper
                                .toDto(true)
                );
    }

}

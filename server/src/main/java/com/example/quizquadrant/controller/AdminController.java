package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.dto.user.UpdateUserNameRequestDto;
import com.example.quizquadrant.dto.user.UserProfileResponseDto;
import com.example.quizquadrant.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

//    ROUTE: 1 => Get details of all the registered users
//    GET: "/api/admin/user/get/all"
    @GetMapping("/user/get/all")
    public ResponseEntity<List<UserProfileResponseDto>> getAllUsers(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return adminService.getAllUsers(pageNumber, pageSize);
    }


//    ROUTE: 2 => Update name of a user
//    PATCH: "/api/admin/user/update/name/:id"
    @PatchMapping("/user/update/name/{id}")
    public ResponseEntity<BooleanResponseDto> updateUserName(
            @PathVariable String id,
            @RequestBody UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception {
        return adminService.updateUserName(updateUserNameRequestDto, id);
    }


//    ROUTE: 3 => Delete a user and all the related data
//    DELETE: "/api/admin/user/delete/:id"
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<BooleanResponseDto> deleteUser(
            @PathVariable String id
    ) throws Exception {
        return adminService.deleteUser(id);
    }

}

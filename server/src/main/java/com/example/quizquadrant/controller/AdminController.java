package com.example.quizquadrant.controller;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.service.AdminService;
import com.example.quizquadrant.service.UserService;
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


    //    user
    @GetMapping("/user/get/all")
    public ResponseEntity<List<UserProfileResponseDto>> getAllUsers(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) throws Exception {
        return adminService.getAllUsers(pageNumber, pageSize);
    }

    @PatchMapping("/user/update/name/{id}")
    public ResponseEntity<BooleanResponseDto> updateUserName(
            @PathVariable String id,
            @RequestBody UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception {
        return adminService.updateUserName(updateUserNameRequestDto, id);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<BooleanResponseDto> deleteUser(
            @PathVariable String id
    ) throws Exception {
        return adminService.deleteUser(id);
    }

}

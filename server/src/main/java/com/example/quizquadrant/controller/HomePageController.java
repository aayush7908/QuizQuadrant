package com.example.quizquadrant.controller;


import com.example.quizquadrant.dto.SubjectDto;
import com.example.quizquadrant.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/homepage")
public class HomePageController {

    private final SubjectService subjectService;


    @Autowired
    public HomePageController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/get-home-page")
    public List<SubjectDto> getAllSubjects () {
        return  subjectService.getAllSubjects();
    }
}

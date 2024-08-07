package com.example.quizquadrant.model;

import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Data
public class ExamResponseKey implements Serializable {
    //private Exam exam;
    private User user;
    private PrivateQuestion privateQuestion;
}

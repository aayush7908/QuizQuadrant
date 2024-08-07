package com.example.quizquadrant.model;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultKey implements Serializable {
    private Exam exam;
    private User user;
}

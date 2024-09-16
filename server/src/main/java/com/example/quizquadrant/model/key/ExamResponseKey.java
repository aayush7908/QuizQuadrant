package com.example.quizquadrant.model.key;

import com.example.quizquadrant.model.ExamQuestion;
import com.example.quizquadrant.model.Option;
import com.example.quizquadrant.model.User;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExamResponseKey implements Serializable {
    private User user;
    private ExamQuestion examQuestion;
    private Option option;
}

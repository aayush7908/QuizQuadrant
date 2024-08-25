package com.example.quizquadrant.model.key;

import com.example.quizquadrant.model.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExamQuestionKey implements Serializable {
    private Exam exam;
    private Question question;
}

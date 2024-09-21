package com.example.quizquadrant.model.key;

import com.example.quizquadrant.model.Question;
import com.example.quizquadrant.model.User;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SavedQuestionKey implements Serializable {
    private User user;
    private Question question;
}

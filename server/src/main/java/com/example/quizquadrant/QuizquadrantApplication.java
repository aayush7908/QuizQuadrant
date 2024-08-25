package com.example.quizquadrant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableTransactionManagement
public class QuizquadrantApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizquadrantApplication.class, args);
    }

}

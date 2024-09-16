package com.example.quizquadrant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableRetry
public class QuizquadrantApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizquadrantApplication.class, args);
    }

}

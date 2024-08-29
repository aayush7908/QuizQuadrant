package com.example.quizquadrant.utils.error;

import com.example.quizquadrant.dto.ErrorResponseDto;
import com.example.quizquadrant.utils.email.EmailDetails;
import com.example.quizquadrant.utils.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class CentralizedErrorHandler {
    private final EmailService emailService;

    @ExceptionHandler(BaseError.class)
    public ResponseEntity<ErrorResponseDto> handleCustomError(BaseError error) {
        if (error.isCritical()) {
            emailService.sendErrorMail(error.getErrorMsg(), error);
        }
        return ResponseEntity
                .status(error.getStatusCode())
                .body(
                        ErrorResponseDto
                                .builder()
                                .errorMessage(error.getErrorMsg())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleCriticalError(Exception error) {
        emailService.sendErrorMail("Unexpected Error", error);
        return ResponseEntity
                .status(500)
                .body(
                        ErrorResponseDto
                                .builder()
                                .errorMessage("Internal Server Error")
                                .build()
                );
    }
}
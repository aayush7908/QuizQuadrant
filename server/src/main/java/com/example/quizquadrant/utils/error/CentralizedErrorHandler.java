package com.example.quizquadrant.utils.error;

import com.example.quizquadrant.dto.ErrorResponseDto;
import com.example.quizquadrant.utils.email.EmailDetails;
import com.example.quizquadrant.utils.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class CentralizedErrorHandler {

    private final EmailService emailService;
    @Value("${DEVELOPER_EMAIL}")
    private String devMail;

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

    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            NoResourceFoundException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponseDto> handleUrlNotFoundError(Exception error) {
        return ResponseEntity
                .status(400)
                .body(
                        ErrorResponseDto
                                .builder()
                                .errorMessage("Bad Request")
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleCriticalError(Exception error) {
        System.out.println(error.getClass());
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

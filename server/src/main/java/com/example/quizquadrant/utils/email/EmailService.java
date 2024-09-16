package com.example.quizquadrant.utils.email;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000)
    )
    @Async
    void sendSimpleMail(
            EmailDetails emailDetails
    ) throws Exception;

    @Recover
    void sendSimpleMailRecovery(Exception e, EmailDetails emailDetails);

}

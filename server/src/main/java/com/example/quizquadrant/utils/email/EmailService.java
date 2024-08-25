package com.example.quizquadrant.utils.email;

public interface EmailService {
    void sendSimpleMail(EmailDetails emailDetails);
    boolean sendMailWithAttachment(EmailDetails emailDetails);
}

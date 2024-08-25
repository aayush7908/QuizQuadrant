package com.example.quizquadrant.utils.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    @Override
    public void sendSimpleMail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getMsgBody());
            javaMailSender.send(simpleMailMessage);
            System.out.println("Email sent");
        } catch(Exception e) {
            System.out.println("Could not send email");
        }
    }

    @Override
    public boolean sendMailWithAttachment(EmailDetails emailDetails) {
        return false;
    }
}
